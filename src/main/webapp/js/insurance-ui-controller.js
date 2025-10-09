// This file depended on "ec_insurance_redesign_status" received from server to switch UI mode.
// After flag removal, remove all branches/logic, and always use classic/standard UI

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
            const response = await fetch('/api/features/status');
            this.featureConfig = await response.json();
            // Do not extract redesign enabled (always false)
            this.isRedesignEnabled = false;
            this.initializeUI();
            this.bindEventHandlers();
        } catch (error) {
            console.error('Failed to initialize Insurance UI Controller:', error);
            this.isRedesignEnabled = false;
            this.initializeUI();
        }
    }

    initializeUI() {
        // Only use standard UI flows
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

    // Other methods remain, but references to isRedesignEnabled or redesign features are no-ops

    getFeatureConfig() {
        return {
            redesignEnabled: false,
            featureConfig: this.featureConfig
        };
    }

    updateFeatureConfiguration(newConfig) {
        // Feature is always off
        this.isRedesignEnabled = false;
    }
}

document.addEventListener('DOMContentLoaded', () => {
    window.insuranceUIController = new InsuranceUIController();
});

if (typeof module !== 'undefined' && module.exports) {
    module.exports = InsuranceUIController;
}
