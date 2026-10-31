# Frontend

This directory contains the frontend files for LabDistrib — the public-facing website and admin dashboard.

## Current Status

✅ **Stage 2 Complete** — Static HTML/CSS pages with placeholder content. No backend connectivity.

## Structure

```
frontend/
├── index.html              — Home / landing page
├── products.html           — Product catalogue listing
├── product-details.html    — Single product detail view
├── categories.html         — Category directory
├── manufacturers.html      — Manufacturer directory
├── contact.html            — Contact info & enquiry form
├── login.html              — Login page
├── admin/
│   ├── dashboard.html      — Admin dashboard
│   ├── products.html       — Product management (placeholder)
│   ├── categories.html     — Category management (placeholder)
│   ├── manufacturers.html  — Manufacturer management (placeholder)
│   └── enquiries.html      — Enquiry queue (placeholder)
├── css/
│   ├── main.css            — Global styles, navigation, footer, utilities
│   ├── catalog.css         — Product/category/manufacturer page styles
│   └── admin.css           — Admin dashboard styles
├── js/
│   ├── app.js              — Navigation toggle, shared utilities
│   └── api.js              — API scaffolding (not yet connected)
└── images/
    └── logo.png            — Business logo (add your logo file here)
```

## Technology

- HTML5 (semantic elements)
- CSS3 (custom properties, Flexbox, CSS Grid, responsive design)
- Vanilla JavaScript (no frameworks)

## How to View

Open any `.html` file directly in your web browser (double-click the file). No server is required for static pages.

## Important Notes

- **No prices displayed** — Products show "Send Enquiry" buttons instead of prices
- **All content is placeholder** — Real products, categories, and manufacturers will be loaded from the database in Stage 6
- **Forms do not submit** — Form functionality will be connected to the backend API in later stages
- **Admin pages are placeholder** — Full admin functionality will be built in Stage 8
- **Logo**: Place your business logo file as `images/logo.png`

## What Comes Next

- **Stage 3**: Spring Boot backend setup
- **Stage 6**: Connect frontend to backend API
- **Stage 7**: Login functionality
- **Stage 8**: Full admin dashboard
