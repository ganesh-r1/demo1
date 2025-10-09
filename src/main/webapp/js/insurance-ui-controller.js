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
            
            // Feature is always disabled after removal
            this.isRedesignEnabled = false;
            
            console.log('Insurance UI Controller initialized with redesign feature:', this.isRedesignEnabled);
            
            // Initialize UI based on feature state
            this.initializeUI();
            this.bindEventHandlers();
            
        } catch (error) {
            console.error('Failed to initialize Insurance UI Controller:', error);
            this.isRedesignEnabled = false;
            this.initializeUI();
        }
    }

    /**
     * Initialize UI components based on feature configuration
     */
    initializeUI() {
        this.setupNavigationUI(this.isRedesignEnabled);
        this.configureClaimsInterface(this.isRedesignEnabled);
        this.initializeProductDisplay(this.isRedesignEnabled);
        
        this.enableStandardFeatures();
    }

    /**
     * Setup navigation UI based on redesign feature state
     */
    setupNavigationUI(redesignEnabled) {
        const navContainer = document.querySelector('.navigation-container');
        if (!navContainer) return;

        // Always standard navigation after flag removal
        navContainer.classList.add('standard-nav');
        this.addStandardNavigationItems();
    }

    /**
     * Configure claims interface based on feature state
     */
    configureClaimsInterface(redesignEnabled) {
        const claimsSection = document.querySelector('.claims-section');
        if (!claimsSection) return;

        this.initializeStandardClaimsForm();
    }

    /**
     * Initialize product display based on feature configuration
     */
    initializeProductDisplay(redesignEnabled) {
        const productGrid = document.querySelector('.product-grid');
        if (!productGrid) return;

        // Always use standard configuration after removal
        const displayConfig = {
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
        this.renderProductCards(displayConfig);
    }

    enableStandardFeatures() {
        console.log('Enabling standard insurance features...');
        this.initializeBasicForms();
        this.enableStandardNotifications();
        this.setupStandardDashboard();
        this.bindStandardEventHandlers();
    }

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
        // Only standard event handlers after flag removal
        this.bindCommonEventHandlers();
        this.bindStandardEventHandlers();
    }

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
            redesignEnabled: this.isRedesignEnabled,
            featureConfig: this.featureConfig
        };
    }

    updateFeatureConfiguration(newConfig) {
        const previousState = this.isRedesignEnabled;
        this.isRedesignEnabled = false;
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
