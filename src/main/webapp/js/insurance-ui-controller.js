/**
 * Insurance UI Controller - Handles UI behavior based on feature flags
 * This file receives feature flag states indirectly from the server
 */

class InsuranceUIController {
    constructor() {
        this.featureConfig = null;
        // this.isRedesignEnabled = false; // removed
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
            
            // this.isRedesignEnabled = this.featureConfig.ec_insurance_redesign_status || false; // removed
            
            console.log('Insurance UI Controller initialized');
            
            // Initialize UI based on feature state (always standard)
            this.initializeUI();
            this.bindEventHandlers();
            
        } catch (error) {
            console.error('Failed to initialize Insurance UI Controller:', error);
            // Fallback to standard mode
            this.initializeUI();
        }
    }

    /**
     * Initialize UI components based on feature configuration
     */
    initializeUI() {
        // Pass feature state to UI initialization methods
        this.setupNavigationUI(false);
        this.configureClaimsInterface(false);
        this.initializeProductDisplay(false);
        
        this.enableStandardFeatures();
    }

    /**
     * Setup navigation UI based on redesign feature state
     */
    setupNavigationUI(redesignEnabled) {
        const navContainer = document.querySelector('.navigation-container');
        if (!navContainer) return;

        // Always standard navigation
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

        // Always standard product display mode
        const displayConfig = this.getProductDisplayConfig(false);
        this.renderProductCards(displayConfig);
    }

    /**
     * Get product display configuration based on feature state
     */
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

    /**
     * Enable standard features
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
     * Bind event handlers
     */
    bindEventHandlers() {
        // Common event handlers
        this.bindCommonEventHandlers();
        this.bindStandardEventHandlers();
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
