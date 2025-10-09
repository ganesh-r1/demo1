<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.example.demo1.feature.FeatureControlCheckUtil" %>

<%
    // REMOVED: InsuranceRedesignEnabled - Feature flag logic
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
    
    <!-- Always standard CSS -->
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
    <!-- REMOVED: EC_INSURANCE_REDESIGN indicator -->

    <div class="container">
        <!-- Header section with conditional rendering -->
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

    <!-- No feature-specific JavaScript for insurance redesign -->
    <script>
        // Common JavaScript for classic UI
        console.log('UI Theme: ${uiTheme}');
    </script>
</body>
</html>
