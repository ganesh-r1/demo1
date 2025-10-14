/**
 * Insurance UI Controller - Handles UI behavior
 */

class InsuranceUIController {
    constructor() {
        this.featureConfig = null;
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
            
            console.log('Insurance UI Controller initialized');
            
            // Initialize UI
            this.initializeUI();
            this.bindEventHandlers();
            
        } catch (error) {
            console.error('Failed to initialize Insurance UI Controller:', error);
            this.initializeUI();
        }
    }

    /**
     * Initialize UI components
     */
    initializeUI() {
        this.setupNavigationUI();
        this.configureClaimsInterface();
        this.initializeProductDisplay();
        this.enableFeatures();
    }

    /**
     * Setup navigation UI
     */
    setupNavigationUI() {
        const navContainer = document.querySelector('.navigation-container');
        if (!navContainer) return;

        navContainer.classList.add('modern-nav');
        this.addAINavigationItems();
        this.enableSmartSearch();
    }

    /**
     * Configure claims interface
     */
    configureClaimsInterface() {
        const claimsSection = document.querySelector('.claims-section');
        if (!claimsSection) return;

        this.initializeAIClaimsProcessor();
        this.enablePhotoClaimsUpload();
        this.setupRealTimeClaimsTracking();
    }

    /**
     * Initialize product display
     */
    initializeProductDisplay() {
        const productGrid = document.querySelector('.product-grid');
        if (!productGrid) return;

        const displayConfig = this.getProductDisplayConfig();
        this.renderProductCards(displayConfig);
    }

    /**
     * Get product display configuration
     */
    getProductDisplayConfig() {
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
    }

    /**
     * Enable features
     */
    enableFeatures() {
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
     * Initialize AI insights panel
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
     * Initialize AI claims processor
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
     * Bind event handlers
     */
    bindEventHandlers() {
        // Common event handlers
        this.bindCommonEventHandlers();
        this.bindEnhancedEventHandlers();
    }

    /**
     * Bind enhanced event handlers
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
     * Toggle AI assistant
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
     * Perform smart search with AI suggestions
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
     * Get current feature configuration
     */
    getFeatureConfig() {
        return {
            featureConfig: this.featureConfig
        };
    }

    /**
     * Update UI when feature configuration changes
     */
    updateFeatureConfiguration(newConfig) {
        this.initializeUI();
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