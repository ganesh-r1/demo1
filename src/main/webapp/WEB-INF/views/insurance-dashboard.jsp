<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.example.demo1.feature.FeatureControlCheckUtil" %>

<%
    // Get the feature flag value and store in page context
    boolean isInsuranceRedesignEnabled = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
    pageContext.setAttribute("insuranceRedesignEnabled", isInsuranceRedesignEnabled);
    
    // Set theme and layout based on feature flag
    String uiTheme = isInsuranceRedesignEnabled ? "modern-redesign" : "classic-theme";
    String layoutClass = isInsuranceRedesignEnabled ? "redesigned-layout" : "standard-layout";
    pageContext.setAttribute("uiTheme", uiTheme);
    pageContext.setAttribute("layoutClass", layoutClass);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Insurance Dashboard</title>
    
    <!-- Conditional CSS based on feature flag -->
    <c:choose>
        <c:when test="${insuranceRedesignEnabled}">
            <link rel="stylesheet" href="/css/modern-insurance-theme.css">
            <link rel="stylesheet" href="/css/enhanced-components.css">
            <script src="/js/ai-powered-features.js"></script>
        </c:when>
        <c:otherwise>
            <link rel="stylesheet" href="/css/classic-insurance-theme.css">
            <script src="/js/standard-features.js"></script>
        </c:otherwise>
    </c:choose>
    
    <style>
        .feature-indicator {
            position: fixed;
            top: 10px;
            right: 10px;
            padding: 5px 10px;
            border-radius: 4px;
            font-size: 12px;
            z-index: 1000;
        }
        
        .redesigned-layout {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        .standard-layout {
            background: #f5f5f5;
            font-family: Arial, sans-serif;
        }
        
        .enhanced-card {
            box-shadow: 0 8px 32px rgba(0,0,0,0.1);
            border-radius: 16px;
            backdrop-filter: blur(10px);
        }
        
        .standard-card {
            border: 1px solid #ddd;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
    </style>
</head>

<body class="${layoutClass}">
    <!-- Feature indicator for debugging -->
    <div class="feature-indicator" style="background-color: ${insuranceRedesignEnabled ? '#4CAF50' : '#FF9800'}; color: white;">
        EC_INSURANCE_REDESIGN: ${insuranceRedesignEnabled ? 'ENABLED' : 'DISABLED'}
    </div>

    <div class="container">
        <!-- Header section with conditional rendering -->
        <header>
            <c:choose>
                <c:when test="${insuranceRedesignEnabled}">
                    <h1 class="modern-title">
                        <i class="ai-icon"></i>
                        Smart Insurance Dashboard
                        <span class="beta-badge">Enhanced</span>
                    </h1>
                    <nav class="modern-nav">
                        <a href="#dashboard" class="nav-item active">Dashboard</a>
                        <a href="#ai-insights" class="nav-item">AI Insights</a>
                        <a href="#digital-claims" class="nav-item">Digital Claims</a>
                        <a href="#risk-assessment" class="nav-item">Risk Assessment</a>
                    </nav>
                </c:when>
                <c:otherwise>
                    <h1 class="classic-title">Insurance Dashboard</h1>
                    <nav class="classic-nav">
                        <a href="#dashboard">Dashboard</a>
                        <a href="#claims">Claims</a>
                        <a href="#policies">Policies</a>
                        <a href="#reports">Reports</a>
                    </nav>
                </c:otherwise>
            </c:choose>
        </header>

        <!-- Main content area -->
        <main class="main-content">
            <!-- Insurance products section -->
            <section class="insurance-products">
                <c:choose>
                    <c:when test="${insuranceRedesignEnabled}">
                        <div class="enhanced-card product-grid">
                            <h2>Enhanced Insurance Products</h2>
                            <div class="product-cards">
                                <div class="product-card ai-powered">
                                    <h3>AI-Powered Risk Assessment</h3>
                                    <p>Advanced machine learning algorithms analyze your risk profile in real-time.</p>
                                    <button class="cta-button enhanced">Get AI Quote</button>
                                </div>
                                <div class="product-card digital-first">
                                    <h3>Digital-First Coverage</h3>
                                    <p>Fully digital insurance experience with instant approvals and claims processing.</p>
                                    <button class="cta-button enhanced">Apply Now</button>
                                </div>
                                <div class="product-card smart-monitoring">
                                    <h3>Smart Monitoring</h3>
                                    <p>IoT-enabled monitoring for proactive risk management and premium adjustments.</p>
                                    <button class="cta-button enhanced">Learn More</button>
                                </div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="standard-card">
                            <h2>Insurance Products</h2>
                            <ul class="product-list">
                                <li>Basic Auto Insurance</li>
                                <li>Home Insurance</li>
                                <li>Life Insurance</li>
                                <li>Health Insurance</li>
                            </ul>
                            <button class="cta-button standard">View All Products</button>
                        </div>
                    </c:otherwise>
                </c:choose>
            </section>

            <!-- Claims processing section -->
            <section class="claims-section">
                <c:if test="${insuranceRedesignEnabled}">
                    <div class="enhanced-card claims-redesigned">
                        <h2>Intelligent Claims Processing</h2>
                        <div class="claims-features">
                            <div class="feature-item">
                                <i class="icon-camera"></i>
                                <h4>Photo Claims</h4>
                                <p>Submit claims instantly using AI-powered photo analysis</p>
                            </div>
                            <div class="feature-item">
                                <i class="icon-chat"></i>
                                <h4>AI Assistant</h4>
                                <p>Get 24/7 support from our intelligent claims assistant</p>
                            </div>
                            <div class="feature-item">
                                <i class="icon-lightning"></i>
                                <h4>Instant Approval</h4>
                                <p>Most claims approved and processed within minutes</p>
                            </div>
                        </div>
                        <button class="cta-button enhanced" onclick="startAIClaim()">Start AI-Powered Claim</button>
                    </div>
                </c:if>
                
                <c:if test="${!insuranceRedesignEnabled}">
                    <div class="standard-card">
                        <h2>File a Claim</h2>
                        <form class="standard-form">
                            <label for="claimType">Claim Type:</label>
                            <select id="claimType" name="claimType">
                                <option value="auto">Auto</option>
                                <option value="home">Home</option>
                                <option value="life">Life</option>
                            </select>
                            
                            <label for="description">Description:</label>
                            <textarea id="description" name="description" rows="4"></textarea>
                            
                            <button type="submit" class="cta-button standard">Submit Claim</button>
                        </form>
                    </div>
                </c:if>
            </section>

            <!-- Analytics and insights -->
            <c:if test="${insuranceRedesignEnabled}">
                <section class="analytics-section">
                    <div class="enhanced-card">
                        <h2>Personalized Insights</h2>
                        <div class="insights-grid">
                            <div class="insight-card">
                                <h4>Risk Score</h4>
                                <div class="score-display">7.2/10</div>
                                <p>Your risk profile has improved by 15% this month</p>
                            </div>
                            <div class="insight-card">
                                <h4>Savings Opportunity</h4>
                                <div class="savings-display">$240/year</div>
                                <p>Switch to our smart monitoring plan</p>
                            </div>
                            <div class="insight-card">
                                <h4>Claims Prediction</h4>
                                <div class="prediction-display">Low Risk</div>
                                <p>AI predicts low claim probability for next 6 months</p>
                            </div>
                        </div>
                    </div>
                </section>
            </c:if>
        </main>
    </div>

    <!-- Feature-specific JavaScript -->
    <script>
        // JavaScript that depends on feature flag
        <c:if test="${insuranceRedesignEnabled}">
            // Enhanced features JavaScript
            function startAIClaim() {
                console.log('Starting AI-powered claim process...');
                // Initialize AI claim wizard
                window.location.href = '/api/claims/ai-wizard';
            }
            
            // Initialize enhanced UI components
            document.addEventListener('DOMContentLoaded', function() {
                initializeAIFeatures();
                loadPersonalizedInsights();
                enableSmartNotifications();
            });
            
            function initializeAIFeatures() {
                console.log('Initializing AI-powered features...');
                // AI feature initialization code
            }
            
            function loadPersonalizedInsights() {
                console.log('Loading personalized insights...');
                // Load AI-generated insights
            }
            
            function enableSmartNotifications() {
                console.log('Enabling smart notifications...');
                // Smart notification system
            }
        </c:if>
        
        <c:if test="${!insuranceRedesignEnabled}">
            // Standard features JavaScript
            document.addEventListener('DOMContentLoaded', function() {
                initializeStandardFeatures();
            });
            
            function initializeStandardFeatures() {
                console.log('Initializing standard features...');
                // Standard feature initialization
            }
        </c:if>
        
        // Common JavaScript for both modes
        console.log('Insurance Redesign Feature: ${insuranceRedesignEnabled}');
        console.log('UI Theme: ${uiTheme}');
    </script>
</body>
</html>
