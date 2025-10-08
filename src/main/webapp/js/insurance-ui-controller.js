/**
 * Insurance UI Controller - Handles UI behavior based on feature flags
 * This file receives feature flag states indirectly from the server
 */

class InsuranceUIController {
    constructor() {
        this.featureConfig = null;
        // REMOVE isRedesignEnabled and logic branching
        this.uiComponents = {};
        this.eventHandlers = {};
        this.init();
    }

    async init() {
        try {
            const response = await fetch('/api/features/status');
            this.featureConfig = await response.json();
            // REMOVE extraction of ec_insurance_redesign_status
            console.log('Insurance UI Controller initialized with redesign feature: REMOVED');
            // Remove conditional branching based on redesign, keep only standard UI
            this.initializeUI();
            this.bindEventHandlers();
        } catch (error) {
            console.error('Failed to initialize Insurance UI Controller:', error);
            this.initializeUI();
        }
    }

    initializeUI() {
        this.setupNavigationUI(false);
        this.configureClaimsInterface(false);
        this.initializeProductDisplay(false);
        this.enableStandardFeatures();
    }

    setupNavigationUI(redesignEnabled) {
        const navContainer = document.querySelector('.navigation-container');
        if (!navContainer) return;
        navContainer.classList.add('standard-nav');
        this.addStandardNavigationItems();
    }

    configureClaimsInterface(redesignEnabled) {
        const claimsSection = document.querySelector('.claims-section');
        if (!claimsSection) return;
        this.initializeStandardClaimsForm();
    }

    initializeProductDisplay(redesignEnabled) {
        const productGrid = document.querySelector('.product-grid');
        if (!productGrid) return;
        const displayConfig = this.getProductDisplayConfig(false);
        this.renderProductCards(displayConfig);
    }

    getProductDisplayConfig(redesignEnabled) {
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

    enableEnhancedFeatures() {
        // REMOVE enhanced features
    }
    enableStandardFeatures() {
        console.log('Enabling standard insurance features...');
        this.initializeBasicForms();
        this.enableStandardNotifications();
        this.setupStandardDashboard();
        this.bindStandardEventHandlers();
    }

    initializeAIInsights() {}
    loadAIInsights() { return []; }
    initializeAIClaimsProcessor() {}
    setupPhotoAnalysis() {}
    async analyzeClaimPhoto(file) {}
    displayPhotoAnalysis(analysis) {}

    initializeStandardClaimsForm() {
        console.log('Initializing standard claims form...');
        const claimsForm = document.querySelector('.standard-claims-form');
        if (!claimsForm) return;
        claimsForm.addEventListener('submit', (event) => {
            event.preventDefault();
            this.submitStandardClaim(new FormData(claimsForm));
        });
    }
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

    bindEventHandlers() {
        this.bindCommonEventHandlers();
        this.bindStandardEventHandlers();
    }

    bindEnhancedEventHandlers() {}

    bindStandardEventHandlers() {
        const basicSearch = document.querySelector('.basic-search-input');
        if (basicSearch) {
            basicSearch.addEventListener('input', (e) => this.performBasicSearch(e.target.value));
        }
        const forms = document.querySelectorAll('.standard-form');
        forms.forEach(form => {
            form.addEventListener('submit', (e) => this.validateStandardForm(e));
        });
    }
    toggleAIAssistant() {}
    async performSmartSearch(query) {}
    performBasicSearch(query) {
        const items = document.querySelectorAll('.searchable-item');
        items.forEach(item => {
            const text = item.textContent.toLowerCase();
            const matches = text.includes(query.toLowerCase());
            item.style.display = matches ? 'block' : 'none';
        });
    }
    getFeatureConfig() {
        return {
            featureConfig: this.featureConfig
        };
    }
    updateFeatureConfiguration(newConfig) {
        // Feature configuration update does not affect redesign, as it is removed
        this.initializeUI();
    }
}

document.addEventListener('DOMContentLoaded', () => {
    window.insuranceUIController = new InsuranceUIController();
});
if (typeof module !== 'undefined' && module.exports) {
    module.exports = InsuranceUIController;
}
