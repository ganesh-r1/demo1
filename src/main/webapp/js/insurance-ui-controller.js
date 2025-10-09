/**
 * Insurance UI Controller - Handles UI behavior based on feature flags
 * This file receives feature flag states indirectly from the server
 */

class InsuranceUIController {
    constructor() {
        this.featureConfig = null;
        // REMOVE: this.isRedesignEnabled
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
            
            // REMOVE: Extract insurance redesign state
            
            console.log('Insurance UI Controller initialized');
            
            // REMOVE: this.initializeUI();
            // REMOVE: this.bindEventHandlers();
            
        } catch (error) {
            console.error('Failed to initialize Insurance UI Controller:', error);
        }
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
