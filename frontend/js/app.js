/**
 * LabDistrib — Main Application JavaScript
 * 
 * This file contains shared functionality used across all pages.
 * Currently handles: mobile navigation toggle, active nav highlighting.
 * 
 * Future stages will add:
 * - Stage 6: API integration (api.js, catalog.js)
 * - Stage 7: Authentication (auth.js)
 * - Stage 8: Admin functionality (admin.js)
 * - Stage 9: Enquiry submission (enquiry.js)
 */

'use strict';

// =============================================================================
// Mobile Navigation Toggle
// =============================================================================
// Handles the hamburger menu on mobile devices.
// When the user clicks the menu button, the navigation slides open/closed.

document.addEventListener('DOMContentLoaded', function() {
    const menuToggle = document.getElementById('menuToggle');
    const mainNav = document.getElementById('mainNav');
    
    if (menuToggle && mainNav) {
        menuToggle.addEventListener('click', function() {
            const isOpen = mainNav.classList.toggle('header__nav--open');
            menuToggle.setAttribute('aria-expanded', isOpen);
        });
        
        // Close menu when clicking outside
        document.addEventListener('click', function(event) {
            if (!menuToggle.contains(event.target) && !mainNav.contains(event.target)) {
                mainNav.classList.remove('header__nav--open');
                menuToggle.setAttribute('aria-expanded', 'false');
            }
        });
        
        // Close menu when window is resized to desktop
        window.addEventListener('resize', function() {
            if (window.innerWidth > 768) {
                mainNav.classList.remove('header__nav--open');
                menuToggle.setAttribute('aria-expanded', 'false');
            }
        });
    }
    
    // =========================================================================
    // Active Navigation Link Highlighting
    // =========================================================================
    // Automatically highlights the current page in the navigation bar.
    // It compares the current URL with each nav link's href.
    
    highlightActiveNavLink();
});

/**
 * Highlights the navigation link that matches the current page URL.
 * This function checks both the public nav and admin sidebar.
 */
function highlightActiveNavLink() {
    const currentPage = window.location.pathname.split('/').pop() || 'index.html';
    
    // Public navigation
    const navLinks = document.querySelectorAll('.header__nav-link');
    navLinks.forEach(function(link) {
        const linkPage = link.getAttribute('href');
        if (linkPage === currentPage) {
            link.classList.add('header__nav-link--active');
        } else {
            link.classList.remove('header__nav-link--active');
        }
    });
    
    // Admin sidebar navigation
    const sidebarLinks = document.querySelectorAll('.admin-sidebar__link');
    sidebarLinks.forEach(function(link) {
        const linkPage = link.getAttribute('href');
        if (linkPage === currentPage) {
            link.classList.add('admin-sidebar__link--active');
        }
    });
}

// =============================================================================
// Utility Functions
// =============================================================================
// These helper functions will be used across multiple pages.

/**
 * Formats a date string into a readable format.
 * @param {string} dateString - ISO date string
 * @returns {string} Formatted date (e.g., "20 Sep 2026")
 */
function formatDate(dateString) {
    const options = { day: 'numeric', month: 'short', year: 'numeric' };
    return new Date(dateString).toLocaleDateString('en-IN', options);
}

/**
 * Shows a simple notification message.
 * This is a placeholder — will be enhanced in later stages.
 * @param {string} message - The message to display
 * @param {string} type - 'success', 'error', or 'info'
 */
function showNotification(message, type) {
    // TODO: Implement proper notification system in Stage 6
    console.log('[' + type.toUpperCase() + '] ' + message);
}

console.log('LabDistrib app.js loaded successfully.');
