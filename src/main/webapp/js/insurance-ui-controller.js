/**
 * Insurance UI Controller - Handles UI behavior based on feature flags
 * This file receives feature flag states indirectly from the server
 */

class InsuranceUIController {
    constructor() {
        this.featureConfig = null;
        this.isRedesignEnabled = false;
        this.uiComponents = {};
        this.eventHandlers = {};
        
        this.init();
    }

    /**
     * Initialize the controller by fetching feature configuration from server
     */
    async init() {
        try {
            // Fetch feature configuration indirectly from server endpoint
            const response = await fetch('/api/features/status');
            this.featureConfig = await response.json();
            
            // Extract insurance redesign state from server response
            this.isRedesignEnabled = this.featureConfig.ec_insurance_redesign_status || false;
            
            console.log('Insurance UI Controller initialized with redesign feature:', this.isRedesignEnabled);
            
            // Initialize UI based on feature state
            this.initializeUI();
            this.bindEventHandlers();
            
        } catch (error) {
            console.error('Failed to initialize Insurance UI Controller:', error);
            // Fallback to standard mode if feature check fails
            this.isRedesignEnabled = false;
            this.initializeUI();
        }
    }

    /**
     * Initialize UI components based on feature configuration
     */
    initializeUI() {
        // Pass feature state to UI initialization methods
        this.setupNavigationUI(this.isRedesignEnabled);
        this.configureClaimsInterface(this.isRedesignEnabled);
        this.initializeProductDisplay(this.isRedesignEnabled);
        
        if (this.isRedesignEnabled) {
            this.enableEnhancedFeatures();
        } else {
            this.enableStandardFeatures();
        }
    }

    /**
     * Setup navigation UI based on redesign feature state
     */
    setupNavigationUI(redesignEnabled) {
        const navContainer = document.querySelector('.navigation-container');
        if (!navContainer) return;

        if (redesignEnabled) {
            // Enhanced navigation with modern styling
            navContainer.classList.add('modern-nav');
            this.addAINavigationItems();
            this.enableSmartSearch();
        } else {
            // Standard navigation
            navContainer.classList.add('standard-nav');
            this.addStandardNavigationItems();
        }
    }

    /**
     * Configure claims interface based on feature state
     */
    configureClaimsInterface(redesignEnabled) {
        const claimsSection = document.querySelector('.claims-section');
        if (!claimsSection) return;

        if (redesignEnabled) {
            this.initializeAIClaimsProcessor();
            this.enablePhotoClaimsUpload();
            this.setupRealTimeClaimsTracking();
        } else {
            this.initializeStandardClaimsForm();
        }
    }

    /**
     * Initialize product display based on feature configuration
     */
    initializeProductDisplay(redesignEnabled) {
        const productGrid = document.querySelector('.product-grid');
        if (!productGrid) return;

        // Use feature state to determine product display mode
        const displayConfig = this.getProductDisplayConfig(redesignEnabled);
        this.renderProductCards(displayConfig);
    }

    /**
     * Get product display configuration based on feature state
     */
    getProductDisplayConfig(redesignEnabled) {
        if (redesignEnabled) {
            return {
                layout: 'enhanced-grid',
                showAIFeatures: true,
                enableInteractiveCards: true,
                products: [
                    'AI-Powered Risk Assessment',
                    'Digital-First Coverage',
                    'Smart Monitoring',
                    'Predictive Analytics'
                ]
            };
        } else {
            return {
                layout: 'standard-list',
                showAIFeatures: false,
                enableInteractiveCards: false,
                products: [
                    'Basic Auto Insurance',
                    'Home Insurance',
                    'Life Insurance',
                    'Health Insurance'
                ]
            };
        }
    }

    /**
     * Enable enhanced features when redesign is active
     */
    enableEnhancedFeatures() {
        console.log('Enabling enhanced insurance features...');
        
        // Initialize AI-powered components
        this.initializeAIInsights();
        this.enableSmartNotifications();
        this.setupPersonalizedDashboard();
        this.initializeRiskAssessmentTools();
        
        // Add enhanced event listeners
        this.bindEnhancedEventHandlers();
    }

    /**
     * Enable standard features when redesign is not active
     */
    enableStandardFeatures() {
        console.log('Enabling standard insurance features...');
        
        // Initialize basic components
        this.initializeBasicForms();
        this.enableStandardNotifications();
        this.setupStandardDashboard();
        
        // Add standard event listeners
        this.bindStandardEventHandlers();
    }

    /**
     * Initialize AI insights panel (enhanced feature)
     */
    initializeAIInsights() {
        const insightsPanel = document.querySelector('.ai-insights-panel');
        if (!insightsPanel) return;

        // Fetch AI insights from server
        this.loadAIInsights().then(insights => {
            this.renderInsights(insights);
        });

        // Setup real-time updates
        this.setupInsightsWebSocket();
    }

    /**
     * Load AI insights from server endpoint
     */
    async loadAIInsights() {
        try {
            const response = await fetch('/api/insurance/ai-insights');
            return await response.json();
        } catch (error) {
            console.error('Failed to load AI insights:', error);
            return [];
        }
    }

    /**
     * Initialize AI claims processor (enhanced feature)
     */
    initializeAIClaimsProcessor() {
        console.log('Initializing AI claims processor...');
        
        // Setup photo upload with AI analysis
        this.setupPhotoAnalysis();
        
        // Initialize claim chatbot
        this.initializeClaimsChatbot();
        
        // Setup automatic claim routing
        this.setupSmartClaimRouting();
    }

    /**
     * Setup photo analysis for claims
     */
    setupPhotoAnalysis() {
        const photoUpload = document.querySelector('.photo-upload-zone');
        if (!photoUpload) return;

        photoUpload.addEventListener('drop', async (event) => {
            event.preventDefault();
            const files = event.dataTransfer.files;
            
            for (let file of files) {
                if (file.type.startsWith('image/')) {
                    await this.analyzeClaimPhoto(file);
                }
            }
        });
    }

    /**
     * Analyze claim photo using AI
     */
    async analyzeClaimPhoto(file) {
        const formData = new FormData();
        formData.append('photo', file);
        
        try {
            const response = await fetch('/api/claims/analyze-photo', {
                method: 'POST',
                body: formData
            });
            
            const analysis = await response.json();
            this.displayPhotoAnalysis(analysis);
            
        } catch (error) {
            console.error('Photo analysis failed:', error);
        }
    }

    /**
     * Display photo analysis results
     */
    displayPhotoAnalysis(analysis) {
        const resultsContainer = document.querySelector('.analysis-results');
        if (!resultsContainer) return;

        resultsContainer.innerHTML = `
            <div class="analysis-card">
                <h4>AI Analysis Results</h4>
                <p><strong>Damage Type:</strong> ${analysis.damageType}</p>
                <p><strong>Severity:</strong> ${analysis.severity}</p>
                <p><strong>Estimated Cost:</strong> $${analysis.estimatedCost}</p>
                <p><strong>Confidence:</strong> ${analysis.confidence}%</p>
            </div>
        `;
    }

    /**
     * Initialize standard claims form (standard feature)
     */
    initializeStandardClaimsForm() {
        console.log('Initializing standard claims form...');
        
        const claimsForm = document.querySelector('.standard-claims-form');
        if (!claimsForm) return;

        claimsForm.addEventListener('submit', (event) => {
            event.preventDefault();
            this.submitStandardClaim(new FormData(claimsForm));
        });
    }

    /**
     * Submit standard claim
     */
    async submitStandardClaim(formData) {
        try {
            const response = await fetch('/api/claims/submit', {
                method: 'POST',
                body: formData
            });
            
            const result = await response.json();
            this.displayClaimSubmissionResult(result);
            
        } catch (error) {
            console.error('Claim submission failed:', error);
        }
    }

    /**
     * Bind event handlers based on feature state
     */
    bindEventHandlers() {
        // Common event handlers
        this.bindCommonEventHandlers();
        
        // Feature-specific handlers
        if (this.isRedesignEnabled) {
            this.bindEnhancedEventHandlers();
        } else {
            this.bindStandardEventHandlers();
        }
    }

    /**
     * Bind enhanced event handlers for redesigned UI
     */
    bindEnhancedEventHandlers() {
        // AI assistant toggle
        const aiToggle = document.querySelector('.ai-assistant-toggle');
        if (aiToggle) {
            aiToggle.addEventListener('click', () => this.toggleAIAssistant());
        }

        // Smart search
        const smartSearch = document.querySelector('.smart-search-input');
        if (smartSearch) {
            smartSearch.addEventListener('input', (e) => this.performSmartSearch(e.target.value));
        }

        // Risk assessment calculator
        const riskCalculator = document.querySelector('.risk-calculator-btn');
        if (riskCalculator) {
            riskCalculator.addEventListener('click', () => this.openRiskAssessment());
        }
    }

    /**
     * Bind standard event handlers for classic UI
     */
    bindStandardEventHandlers() {
        // Basic search
        const basicSearch = document.querySelector('.basic-search-input');
        if (basicSearch) {
            basicSearch.addEventListener('input', (e) => this.performBasicSearch(e.target.value));
        }

        // Standard form validations
        const forms = document.querySelectorAll('.standard-form');
        forms.forEach(form => {
            form.addEventListener('submit', (e) => this.validateStandardForm(e));
        });
    }

    /**
     * Toggle AI assistant (enhanced feature)
     */
    toggleAIAssistant() {
        const assistant = document.querySelector('.ai-assistant-panel');
        if (assistant) {
            assistant.classList.toggle('active');
            
            if (assistant.classList.contains('active')) {
                this.initializeAIChat();
            }
        }
    }

    /**
     * Perform smart search with AI suggestions (enhanced feature)
     */
    async performSmartSearch(query) {
        if (query.length < 2) return;

        try {
            const response = await fetch(`/api/search/smart?q=${encodeURIComponent(query)}`);
            const suggestions = await response.json();
            this.displaySmartSuggestions(suggestions);
        } catch (error) {
            console.error('Smart search failed:', error);
        }
    }

    /**
     * Perform basic search (standard feature)
     */
    performBasicSearch(query) {
        const items = document.querySelectorAll('.searchable-item');
        items.forEach(item => {
            const text = item.textContent.toLowerCase();
            const matches = text.includes(query.toLowerCase());
            item.style.display = matches ? 'block' : 'none';
        });
    }

    /**
     * Get current feature configuration
     */
    getFeatureConfig() {
        return {
            redesignEnabled: this.isRedesignEnabled,
            featureConfig: this.featureConfig
        };
    }

    /**
     * Update UI when feature configuration changes
     */
    updateFeatureConfiguration(newConfig) {
        const previousState = this.isRedesignEnabled;
        this.isRedesignEnabled = newConfig.ec_insurance_redesign_status || false;
        
        // Reinitialize UI if feature state changed
        if (previousState !== this.isRedesignEnabled) {
            console.log('Feature state changed, reinitializing UI...');
            this.initializeUI();
        }
    }
}

// Initialize the controller when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    window.insuranceUIController = new InsuranceUIController();
});

// Export for module usage
if (typeof module !== 'undefined' && module.exports) {
    module.exports = InsuranceUIController;
}
