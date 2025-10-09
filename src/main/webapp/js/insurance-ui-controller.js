/**
 * Insurance UI Controller - Handles UI behavior. EC_INSURANCE_REDESIGN is always OFF (removed).
 */

class InsuranceUIController {
    constructor() {
        this.featureConfig = null;
        this.isRedesignEnabled = false;
        this.uiComponents = {};
        this.eventHandlers = {};
        this.init();
    }

    async init() {
        try {
            // Fetch feature configuration (insurance redesign always disabled)
            const response = await fetch('/api/features/status');
            this.featureConfig = await response.json();
            // Always false
            this.isRedesignEnabled = false;
            this.initializeUI();
            this.bindEventHandlers();
        } catch (error) {
            this.isRedesignEnabled = false;
            this.initializeUI();
        }
    }

    initializeUI() {
        // Always standard features
        this.setupNavigationUI(false);
        this.configureClaimsInterface(false);
        this.initializeProductDisplay(false);
        this.enableStandardFeatures();
    }

    setupNavigationUI() {
        const navContainer = document.querySelector('.navigation-container');
        if (!navContainer) return;
        navContainer.classList.add('standard-nav');
        this.addStandardNavigationItems && this.addStandardNavigationItems();
    }

    configureClaimsInterface() {
        const claimsSection = document.querySelector('.claims-section');
        if (!claimsSection) return;
        this.initializeStandardClaimsForm && this.initializeStandardClaimsForm();
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
        this.renderProductCards && this.renderProductCards(displayConfig);
    }

    enableStandardFeatures() {
        this.initializeBasicForms && this.initializeBasicForms();
        this.enableStandardNotifications && this.enableStandardNotifications();
        this.setupStandardDashboard && this.setupStandardDashboard();
        this.bindStandardEventHandlers && this.bindStandardEventHandlers();
    }

    bindEventHandlers() {
        this.bindCommonEventHandlers && this.bindCommonEventHandlers();
        this.bindStandardEventHandlers && this.bindStandardEventHandlers();
    }

    getFeatureConfig() {
        return {
            redesignEnabled: false,
            featureConfig: this.featureConfig
        };
    }

    updateFeatureConfiguration(_newConfig) {
        // Redesign feature cannot be enabled any longer
    }
}

document.addEventListener('DOMContentLoaded', () => {
    window.insuranceUIController = new InsuranceUIController();
});

if (typeof module !== 'undefined' && module.exports) {
    module.exports = InsuranceUIController;
}
