import asyncio
import os
import sys
import json
import subprocess
from mcp import ClientSession, StdioServerParameters
from mcp.client.stdio import stdio_client
from langchain_mcp_adapters.tools import load_mcp_tools
from langchain_anthropic import ChatAnthropic
from langgraph.prebuilt import create_react_agent

def get_pr_details(repo, pr_num):
    env = os.environ.copy()
    env["GH_TOKEN"] = os.getenv("GITHUB_PERSONAL_ACCESS_TOKEN")
    result = subprocess.run(
        ["gh", "pr", "view", str(pr_num), "--repo", repo, "--json", "headRefName"],
        capture_output=True, text=True, env=env
    )
    return json.loads(result.stdout)

async def run_pr_reviewer(repo_name: str, pr_id: str, workspace_dir: str):
    model = ChatAnthropic(model="claude-sonnet-4-6", temperature=0)
    
    # Use --quiet to keep the pipe clean
    gh_params = StdioServerParameters(
        command="npx", args=["--quiet", "-y", "@modelcontextprotocol/server-github@latest"],
        env={**os.environ, "GITHUB_PERSONAL_ACCESS_TOKEN": os.getenv("GITHUB_PERSONAL_ACCESS_TOKEN")}
    )
    fs_params = StdioServerParameters(
        command="npx", args=["--quiet", "-y", "@modelcontextprotocol/server-filesystem@latest", workspace_dir],
        env=os.environ
    )

    async with stdio_client(gh_params) as (gh_r, gh_w), stdio_client(fs_params) as (fs_r, fs_w):
        async with ClientSession(gh_r, gh_w) as gh_s, ClientSession(fs_r, fs_w) as fs_s:
            await gh_s.initialize(); await fs_s.initialize()
            
            agent = create_react_agent(model, await load_mcp_tools(gh_s) + await load_mcp_tools(fs_s))
            branch = get_pr_details(repo_name, pr_id)['headRefName']

            prompt = f"Review PR #{pr_id} in {repo_name} (branch: {branch}). " \
                     "1. Use get_pull_request_files. " \
                     "2. If additions=0, use create_pull_request_review with a concise body. " \
                     "3. If additions>0, use the comments array for '+' lines. " \
                     "4. NO TEXT OUTPUT outside of tool calls."

            try:
                async for chunk in agent.astream({"messages": [("user", prompt)]}, stream_mode="values"):
                    msg = chunk["messages"][-1]
                    if hasattr(msg, 'type') and msg.type == "ai" and msg.content:
                        print(f"Agent Action: {msg.content}")
            except Exception as e:
                print(f"Error: {e}")

if __name__ == "__main__":
    asyncio.run(run_pr_reviewer(sys.argv[1], sys.argv[2], sys.argv[3]))
