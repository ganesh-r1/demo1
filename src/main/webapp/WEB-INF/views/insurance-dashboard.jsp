<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.example.demo1.feature.FeatureControlCheckUtil" %>

<%
    // Get the feature flag value and store in page context
    // REMOVE: boolean isInsuranceRedesignEnabled = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
    // REMOVE: pageContext.setAttribute("insuranceRedesignEnabled", isInsuranceRedesignEnabled);
    
    // REMOVE: Set theme and layout based on feature flag
    String uiTheme = "classic-theme";
    String layoutClass = "standard-layout";
    pageContext.setAttribute("uiTheme", uiTheme);
    pageContext.setAttribute("layoutClass", layoutClass);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Insurance Dashboard</title>
    
    <!-- Conditional CSS: Only classic/standard -->
    <link rel="stylesheet" href="/css/classic-insurance-theme.css">
    <script src="/js/standard-features.js"></script>
    
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
            display: none;
        }
        
        .standard-layout {
            background: #f5f5f5;
            font-family: Arial, sans-serif;
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
    <div class="feature-indicator" style="background-color: #FF9800; color: white;">
        INSURANCE UI: STANDARD MODE
    </div>

    <div class="container">
        <!-- Header section (classic) -->
        <header>
            <h1 class="classic-title">Insurance Dashboard</h1>
            <nav class="classic-nav">
                <a href="#dashboard">Dashboard</a>
                <a href="#claims">Claims</a>
                <a href="#policies">Policies</a>
                <a href="#reports">Reports</a>
            </nav>
        </header>

        <!-- Main content area -->
        <main class="main-content">
            <!-- Insurance products section -->
            <section class="insurance-products">
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
            </section>

            <!-- Claims processing section -->
            <section class="claims-section">
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
            </section>
        </main>
    </div>

    <!-- JavaScript for standard features only -->
    <script>
        // Common JavaScript for standard mode
        console.log('Insurance UI: STANDARD MODE');
        console.log('UI Theme: ${uiTheme}');
    </script>
</body>
</html>
