/**
 * Insurance UI Controller - Handles UI behavior based on feature flags
 * This file receives feature flag states indirectly from the server
 */

class InsuranceUIController {
    constructor() {
        this.featureConfig = null;
        // REMOVED: this.isRedesignEnabled = false;
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
            
            // REMOVED: Extract insurance redesign state

            // MODIFIED: Always standard features
            this.initializeUI();
            this.bindEventHandlers();
            
        } catch (error) {
            console.error('Failed to initialize Insurance UI Controller:', error);
            this.initializeUI();
        }
    }

    /**
     * Initialize UI components (now always standard)
     */
    initializeUI() {
        this.setupNavigationUI(false);
        this.configureClaimsInterface(false);
        this.initializeProductDisplay(false);
        this.enableStandardFeatures();
    }

    /**
     * Setup navigation UI always classic
     */
    setupNavigationUI() {
        const navContainer = document.querySelector('.navigation-container');
        if (!navContainer) return;
        navContainer.classList.add('standard-nav');
        this.addStandardNavigationItems();
    }

    configureClaimsInterface() {
        const claimsSection = document.querySelector('.claims-section');
        if (!claimsSection) return;
        this.initializeStandardClaimsForm();
    }

    initializeProductDisplay() {
        const productGrid = document.querySelector('.product-grid');
        if (!productGrid) return;
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

    // REMOVED: All AI/enhanced methods

    /**
     * Other unchanged logic and methods
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
        // No dynamic feature config anymore, always standard
        this.featureConfig = newConfig;
        this.initializeUI();
    }
}

// Initialize the controller when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    window.insuranceUIController = new InsuranceUIController();
});

if (typeof module !== 'undefined' && module.exports) {
    module.exports = InsuranceUIController;
}
