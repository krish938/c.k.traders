# Recovery & Debugging Map

This document helps you trace problems to their source. When something isn't working, use these checklists to identify which file or layer is causing the issue.

**How to use this guide**: Start from the top of each checklist (closest to the user) and work your way down (toward the database). The problem is usually in the first file that doesn't behave as expected.

---

### Product Problems

**Symptom**: Product page is not displaying / products not loading

| Step | File/Component | What to check | Status |
|------|---------------|---------------|--------|
| 1 | `frontend/js/products.js` | Is the JavaScript making the API call? Is there a console error? | NOT IMPLEMENTED |
| 2 | `frontend/products.html` | Is the HTML structure correct? Are the IDs matching the JS? | NOT IMPLEMENTED |
| 3 | Browser DevTools → Network tab | Is the API request being sent? What status code is returned? | N/A |
| 4 | `ProductController.java` | Is the endpoint mapped correctly? Is it receiving the request? | NOT IMPLEMENTED |
| 5 | `ProductService.java` | Is the service method being called? Any business logic errors? | NOT IMPLEMENTED |
| 6 | `ProductRepository.java` | Is the repository query correct? | NOT IMPLEMENTED |
| 7 | `Product.java` (Entity) | Are the field mappings correct? Any Hibernate annotations wrong? | NOT IMPLEMENTED |
| 8 | MySQL Database | Does the products table exist? Does it have data? | NOT IMPLEMENTED |

---

### Category Problems

**Symptom**: Categories not displaying / category API not working

| Step | File/Component | What to check | Status |
|------|---------------|---------------|--------|
| 1 | `frontend/js/categories.js` | API call and console errors | NOT IMPLEMENTED |
| 2 | `frontend/categories.html` | HTML structure | NOT IMPLEMENTED |
| 3 | Browser DevTools → Network tab | Request/response inspection | N/A |
| 4 | `CategoryController.java` | Endpoint mapping | NOT IMPLEMENTED |
| 5 | `CategoryService.java` | Business logic | NOT IMPLEMENTED |
| 6 | `CategoryRepository.java` | Database query | NOT IMPLEMENTED |
| 7 | `Category.java` (Entity) | Field mappings | NOT IMPLEMENTED |
| 8 | MySQL Database | Table and data | NOT IMPLEMENTED |

---

### Manufacturer Problems

**Symptom**: Manufacturers not displaying / manufacturer API not working

| Step | File/Component | What to check | Status |
|------|---------------|---------------|--------|
| 1 | `frontend/js/manufacturers.js` | API call and console errors | NOT IMPLEMENTED |
| 2 | `frontend/manufacturers.html` | HTML structure | NOT IMPLEMENTED |
| 3 | Browser DevTools → Network tab | Request/response inspection | N/A |
| 4 | `ManufacturerController.java` | Endpoint mapping | NOT IMPLEMENTED |
| 5 | `ManufacturerService.java` | Business logic | NOT IMPLEMENTED |
| 6 | `ManufacturerRepository.java` | Database query | NOT IMPLEMENTED |
| 7 | `Manufacturer.java` (Entity) | Field mappings | NOT IMPLEMENTED |
| 8 | MySQL Database | Table and data | NOT IMPLEMENTED |

---

### Enquiry Problems

**Symptom**: Enquiry form not submitting / enquiries not showing in admin

| Step | File/Component | What to check | Status |
|------|---------------|---------------|--------|
| 1 | `frontend/js/enquiry.js` | Form submission JavaScript | NOT IMPLEMENTED |
| 2 | `frontend/contact.html` or product page | Form HTML | NOT IMPLEMENTED |
| 3 | Browser DevTools → Network tab | POST request inspection | N/A |
| 4 | `EnquiryController.java` | Endpoint mapping | NOT IMPLEMENTED |
| 5 | `EnquiryService.java` | Business logic, validation | NOT IMPLEMENTED |
| 6 | `EnquiryRepository.java` | Database query | NOT IMPLEMENTED |
| 7 | `Enquiry.java` / `EnquiryItem.java` (Entity) | Field mappings | NOT IMPLEMENTED |
| 8 | MySQL Database | Tables and data | NOT IMPLEMENTED |

---

### Login / Authentication Problems

**Symptom**: Login not working / getting 401/403 errors

| Step | File/Component | What to check | Status |
|------|---------------|---------------|--------|
| 1 | `frontend/js/login.js` | Login form submission, token storage | NOT IMPLEMENTED |
| 2 | `frontend/login.html` | Form HTML | NOT IMPLEMENTED |
| 3 | Browser DevTools → Network tab | Login request, response, status code | N/A |
| 4 | `AuthController.java` | Login endpoint | NOT IMPLEMENTED |
| 5 | `AuthService.java` | Credential verification | NOT IMPLEMENTED |
| 6 | `SecurityConfig.java` | Spring Security configuration | NOT IMPLEMENTED |
| 7 | `JwtService.java` | Token generation/validation | NOT IMPLEMENTED |
| 8 | `UserRepository.java` | User lookup | NOT IMPLEMENTED |
| 9 | `User.java` (Entity) | User entity mapping | NOT IMPLEMENTED |
| 10 | MySQL Database | Users table, password hash | NOT IMPLEMENTED |

---

### Database Connection Problems

**Symptom**: Application won't start / database errors in console

| Step | File/Component | What to check | Status |
|------|---------------|---------------|--------|
| 1 | `application.properties` | Database URL, username, password correct? | NOT IMPLEMENTED |
| 2 | MySQL Server | Is MySQL running? Can you connect via command line? | NOT IMPLEMENTED |
| 3 | Database | Does the database exist? Has it been created? | NOT IMPLEMENTED |
| 4 | Entity classes | Are JPA annotations correct? | NOT IMPLEMENTED |
| 5 | Repository interfaces | Do they extend the correct JPA interface? | NOT IMPLEMENTED |
| 6 | `pom.xml` | Is the MySQL connector dependency present? | NOT IMPLEMENTED |
| 7 | Hibernate logs | Enable hibernate.show_sql=true for debugging | NOT IMPLEMENTED |

---

### General Debugging Tips

- Always check the browser console (F12) first for frontend issues
- Always check the Java console/terminal for backend errors
- Read the error message carefully — it usually tells you exactly what's wrong
- Stack traces read from bottom to top — the root cause is at the bottom
- Google the error message if you don't understand it
- Check that all files are saved before testing
