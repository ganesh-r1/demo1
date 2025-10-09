class InsuranceUIController {
    constructor() {
        this.featureConfig = null;
        this.isRedesignEnabled = true;
        this.uiComponents = {};
        this.eventHandlers = {};
        
        this.init();
    }

    async init() {
        try {
            const response = await fetch('/api/features/status');
            this.featureConfig = await response.json();
            this.isRedesignEnabled = true;
            console.log('Insurance UI Controller initialized with redesign feature:', this.isRedesignEnabled);
            this.initializeUI();
            this.bindEventHandlers();
        } catch (error) {
            console.error('Failed to initialize Insurance UI Controller:', error);
            this.isRedesignEnabled = true;
            this.initializeUI();
        }
    }

    initializeUI() {
        this.setupNavigationUI(this.isRedesignEnabled);
        this.configureClaimsInterface(this.isRedesignEnabled);
        this.initializeProductDisplay(this.isRedesignEnabled);
        this.enableEnhancedFeatures();
    }

    setupNavigationUI(redesignEnabled) {
        const navContainer = document.querySelector('.navigation-container');
        if (!navContainer) return;
        navContainer.classList.add('modern-nav');
        this.addAINavigationItems();
        this.enableSmartSearch();
    }

    configureClaimsInterface(redesignEnabled) {
        const claimsSection = document.querySelector('.claims-section');
        if (!claimsSection) return;
        this.initializeAIClaimsProcessor();
        this.enablePhotoClaimsUpload();
        this.setupRealTimeClaimsTracking();
    }

    initializeProductDisplay(redesignEnabled) {
        const productGrid = document.querySelector('.product-grid');
        if (!productGrid) return;
        const displayConfig = this.getProductDisplayConfig(redesignEnabled);
        this.renderProductCards(displayConfig);
    }

    getProductDisplayConfig(redesignEnabled) {
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

    enableEnhancedFeatures() {
        console.log('Enabling enhanced insurance features...');
        this.initializeAIInsights();
        this.enableSmartNotifications();
        this.setupPersonalizedDashboard();
        this.initializeRiskAssessmentTools();
        this.bindEnhancedEventHandlers();
    }

    enableStandardFeatures() {
    }

    initializeAIInsights() {
        const insightsPanel = document.querySelector('.ai-insights-panel');
        if (!insightsPanel) return;
        this.loadAIInsights().then(insights => {
            this.renderInsights(insights);
        });
        this.setupInsightsWebSocket();
    }

    async loadAIInsights() {
        try {
            const response = await fetch('/api/insurance/ai-insights');
            return await response.json();
        } catch (error) {
            console.error('Failed to load AI insights:', error);
            return [];
        }
    }

    initializeAIClaimsProcessor() {
        console.log('Initializing AI claims processor...');
        this.setupPhotoAnalysis();
        this.initializeClaimsChatbot();
        this.setupSmartClaimRouting();
    }

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

    initializeStandardClaimsForm() {
    }

    async submitStandardClaim(formData) {
    }

    bindEventHandlers() {
        this.bindCommonEventHandlers();
        this.bindEnhancedEventHandlers();
    }

    bindEnhancedEventHandlers() {
        const aiToggle = document.querySelector('.ai-assistant-toggle');
        if (aiToggle) {
            aiToggle.addEventListener('click', () => this.toggleAIAssistant());
        }
        const smartSearch = document.querySelector('.smart-search-input');
        if (smartSearch) {
            smartSearch.addEventListener('input', (e) => this.performSmartSearch(e.target.value));
        }
        const riskCalculator = document.querySelector('.risk-calculator-btn');
        if (riskCalculator) {
            riskCalculator.addEventListener('click', () => this.openRiskAssessment());
        }
    }

    bindStandardEventHandlers() {
    }

    toggleAIAssistant() {
        const assistant = document.querySelector('.ai-assistant-panel');
        if (assistant) {
            assistant.classList.toggle('active');
            if (assistant.classList.contains('active')) {
                this.initializeAIChat();
            }
        }
    }

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

    performBasicSearch(query) {
    }

    getFeatureConfig() {
        return {
            redesignEnabled: this.isRedesignEnabled,
            featureConfig: this.featureConfig
        };
    }

    updateFeatureConfiguration(newConfig) {
        const previousState = this.isRedesignEnabled;
        this.isRedesignEnabled = true;
        if (previousState !== this.isRedesignEnabled) {
            console.log('Feature state changed, reinitializing UI...');
            this.initializeUI();
        }
    }
}

document.addEventListener('DOMContentLoaded', () => {
    window.insuranceUIController = new InsuranceUIController();
});

if (typeof module !== 'undefined' && module.exports) {
    module.exports = InsuranceUIController;
}
