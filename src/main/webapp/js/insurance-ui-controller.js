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
            
            console.log('Insurance UI Controller initialized.');
            
            // REMOVE: Initialize UI based on feature state
            // REMOVE: this.initializeUI();
            this.bindEventHandlers();
            
        } catch (error) {
            console.error('Failed to initialize Insurance UI Controller:', error);
            // REMOVE: Fallback to standard mode if feature check fails
            // REMOVE: this.isRedesignEnabled = false;
            // REMOVE: this.initializeUI();
        }
    }

    /**
     * REMOVE: initializeUI and all methods referring to redesignEnabled
     */
    // REMOVE: initializeUI() { ... }
    // REMOVE: setupNavigationUI(redesignEnabled) { ... }
    // REMOVE: configureClaimsInterface(redesignEnabled) { ... }
    // REMOVE: initializeProductDisplay(redesignEnabled) { ... }
    // REMOVE: getProductDisplayConfig(redesignEnabled) { ... }
    // REMOVE: enableEnhancedFeatures() { ... }
    // REMOVE: enableStandardFeatures() { ... }

    /**
     * REMOVED methods for enhanced/standard feature events, AI Insights, Claims Processor, etc.
     */

    /**
     * Bind event handlers
     */
    bindEventHandlers() {
        // Common event handlers
        this.bindCommonEventHandlers();
        // REMOVE: Feature-specific handlers for redesignEnabled
    }

    /**
     * Common event handlers
     */
    bindCommonEventHandlers() {
        // Basic search
        const basicSearch = document.querySelector('.basic-search-input');
        if (basicSearch) {
            basicSearch.addEventListener('input', (e) => this.performBasicSearch(e.target.value));
        }
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
     * REMOVE: getFeatureConfig() and updateFeatureConfiguration()
     */
}

// Initialize the controller when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    window.insuranceUIController = new InsuranceUIController();
});

// Export for module usage
if (typeof module !== 'undefined' && module.exports) {
    module.exports = InsuranceUIController;
}
