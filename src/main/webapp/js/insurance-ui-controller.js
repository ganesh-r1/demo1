/**
 * Insurance UI Controller - Handles UI behavior based on feature flags
 * This file receives feature flag states indirectly from the server
 */

class InsuranceUIController {
    constructor() {
        this.featureConfig = null;
        // REMOVED: this.isRedesignEnabled
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
            
            // REMOVED: Extract insurance redesign state from server response
            // this.isRedesignEnabled = this.featureConfig.ec_insurance_redesign_status || false;
            
            console.log('Insurance UI Controller initialized');
            
            // Initialize UI
            this.initializeUI();
            this.bindEventHandlers();
            
        } catch (error) {
            console.error('Failed to initialize Insurance UI Controller:', error);
            // Fallback to standard mode
            this.initializeUI();
        }
    }

    /**
     * Initialize UI components
     */
    initializeUI() {
        // NO LONGER PASS FEATURE STATE
        this.setupNavigationUI();
        this.configureClaimsInterface();
        this.initializeProductDisplay();
        
        this.enableStandardFeatures();
    }

    /**
     * Setup navigation UI (always standard now)
     */
    setupNavigationUI() {
        const navContainer = document.querySelector('.navigation-container');
        if (!navContainer) return;
        navContainer.classList.add('standard-nav');
        this.addStandardNavigationItems();
    }

    /**
     * Configure claims interface (always standard now)
     */
    configureClaimsInterface() {
        const claimsSection = document.querySelector('.claims-section');
        if (!claimsSection) return;
        this.initializeStandardClaimsForm();
    }

    /**
     * Initialize product display (always standard now)
     */
    initializeProductDisplay() {
        const productGrid = document.querySelector('.product-grid');
        if (!productGrid) return;
        const displayConfig = this.getProductDisplayConfig();
        this.renderProductCards(displayConfig);
    }

    /**
     * Get product display configuration (always standard now)
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
        // Initialize basic components
        this.initializeBasicForms();
        this.enableStandardNotifications();
        this.setupStandardDashboard();
        // Add standard event listeners
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
     * Bind event handlers (always standard)
     */
    bindEventHandlers() {
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
            featureConfig: this.featureConfig
        };
    }

    updateFeatureConfiguration(newConfig) {
        // NO-OP: No insurance redesign support
    }
}

// Initialize the controller when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    window.insuranceUIController = new InsuranceUIController();
});

if (typeof module !== 'undefined' && module.exports) {
    module.exports = InsuranceUIController;
}
