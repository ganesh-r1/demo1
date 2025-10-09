/**
 * Insurance UI Controller - Handles UI behavior
 * Feature flag 'EC_INSURANCE_REDESIGN' functionality removed - UI is always in legacy (standard) mode
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
     * Initialize the controller
     */
    async init() {
        try {
            // REMOVED: Fetch feature configuration/flag from server
            // DEFAULT: Always use standard mode
            this.featureConfig = {};
            // REMOVED: this.isRedesignEnabled

            // Initialize UI (legacy)
            this.initializeUI();
            this.bindEventHandlers();
            
        } catch (error) {
            // Fallback to standard mode
            this.initializeUI();
        }
    }

    /**
     * Initialize UI components in standard mode
     */
    initializeUI() {
        // Render standard UI always
        this.setupNavigationUI();
        this.configureClaimsInterface();
        this.initializeProductDisplay();
        this.enableStandardFeatures();
    }

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
        this.renderProductCards(this.getProductDisplayConfig());
    }

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

    enableStandardFeatures() {
        // Only standard features, AI-powered features removed
        this.initializeBasicForms();
        this.enableStandardNotifications();
        this.setupStandardDashboard();
        this.bindStandardEventHandlers();
    }

    // The rest of the methods remain as standard-only or no-op for enhanced features

    initializeStandardClaimsForm() {
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
            // Handle error in form submit
        }
    }

    bindEventHandlers() {
        // Standard only event handlers
        this.bindCommonEventHandlers && this.bindCommonEventHandlers();
        this.bindStandardEventHandlers();
    }

    bindStandardEventHandlers() {
        // Basic search
        const basicSearch = document.querySelector('.basic-search-input');
        if (basicSearch) {
            basicSearch.addEventListener('input', (e) => this.performBasicSearch(e.target.value));
        }
        // Standard form validations
        const forms = document.querySelectorAll('.standard-form');
        forms.forEach(form => {
            form.addEventListener('submit', (e) => this.validateStandardForm && this.validateStandardForm(e));
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

    // ... rest of the controller can be deleted/preserved according to use
}

// Initialize the controller when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    window.insuranceUIController = new InsuranceUIController();
});

// Export for module usage
if (typeof module !== 'undefined' && module.exports) {
    module.exports = InsuranceUIController;
}
