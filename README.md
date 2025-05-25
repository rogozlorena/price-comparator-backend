# Price Comparator Backend

**Price Comparator** is a Spring Boot application that allows users to compare product prices across multiple stores, track discount history, and receive recommendations for better value purchases. The system also supports monitoring price changes and filtering trends by store, category, or brand.

---

## Features

- **Best Discounts**  
  Lists products with the highest current percentage discounts across all tracked stores.

- **New Discounts**  
  Shows discounts that have been added within the last 24 hours.

- **Product Substitutes & Recommendations**  
  Identifies alternative products with better price-per-unit value.

- **Dynamic Price History**  
  Provides time series price data to help visualize price trends for products, filterable by store, category, or brand.

- **Custom Price Alerts (Planned)**  
  Allows users to set a target price and receive alerts when a product's price drops to or below that value.

- **Basket Optimization (Planned)**  
  Helps users split shopping baskets across stores to minimize total cost.

---

## Technology Stack

- Java 17
- Spring Boot 3+
- Spring Data JPA
- Hibernate
- Lombok
- REST API (JSON)
- MySQL or PostgreSQL (configurable)
- Maven

---

## Project Structure

