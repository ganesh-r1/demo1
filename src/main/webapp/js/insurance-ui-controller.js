/**
 * Insurance UI Controller - Handles UI behavior based on feature flags
 * This file receives feature flag states indirectly from the server
 */

class InsuranceUIController {
    constructor() {
        this.featureConfig = null;
        // REMOVE: this.isRedesignEnabled = false;
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
            
            // REMOVE: Extract insurance redesign state from server response
            // REMOVE: this.isRedesignEnabled = this.featureConfig.ec_insurance_redesign_status || false;
            
            console.log('Insurance UI Controller initialized with redesign feature: REMOVED');
            
            // Initialize UI without insurance redesign
            this.initializeUI();
            this.bindEventHandlers();
            
        } catch (error) {
            console.error('Failed to initialize Insurance UI Controller:', error);
            // REMOVE: Fallback to standard mode if feature check fails
            this.initializeUI();
        }
    }

    /**
     * Initialize UI components
     */
    initializeUI() {
        // Always classic UI, insurance redesign removed
        this.setupNavigationUI(false);
        this.configureClaimsInterface(false);
        this.initializeProductDisplay(false);
        this.enableStandardFeatures();
    }

    /**
     * Setup navigation UI
     */
    setupNavigationUI() {
        const navContainer = document.querySelector('.navigation-container');
        if (!navContainer) return;

        // Standard navigation
        navContainer.classList.add('standard-nav');
        this.addStandardNavigationItems();
    }

    /**
     * Configure claims interface
     */
    configureClaimsInterface() {
        const claimsSection = document.querySelector('.claims-section');
        if (!claimsSection) return;
        this.initializeStandardClaimsForm();
    }

    /**
     * Initialize product display
     */
    initializeProductDisplay() {
        const productGrid = document.querySelector('.product-grid');
        if (!productGrid) return;

        // Only standard products
        const displayConfig = this.getProductDisplayConfig(false);
        this.renderProductCards(displayConfig);
    }

    /**
     * Get product display configuration
     */
    getProductDisplayConfig() {
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
        this.initializeBasicForms();
        this.enableStandardNotifications();
        this.setupStandardDashboard();
        this.bindStandardEventHandlers();
    }

    /**
     * Initialize standard claims form
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
     * Bind event handlers (only standard, no redesign)
     */
    bindEventHandlers() {
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
        // No insurance redesign, nothing to update
    }
}

// Initialize the controller when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    window.insuranceUIController = new InsuranceUIController();
});

if (typeof module !== 'undefined' && module.exports) {
    module.exports = InsuranceUIController;
}
