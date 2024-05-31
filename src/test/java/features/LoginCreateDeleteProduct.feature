#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Testing of Ecommerce REST APIs # https://rahulshettyacademy.com/client/auth/login
  I want to use this template for my feature file


@tag1
Scenario Outline: Testing of End to Product Cycle: Login Create and Delete
    Given I am able to call API  "Login" using email "<userEmail>" and password "<userPassword>"
    And the "message" is "Login Successfully" 
    When I call API "CreateProduct" with product-name "<productName>" productSubCategory	 "<productSubCategory>"  productPrice 	"<productPrice>"
    Then the "message" is "Product Added Successfully"
    And I am able to call API  "DeleteProduct" successfully with  "message" "Product Deleted Successfully"


Examples: 
	|userEmail							|userPassword|productName |productSubCategory|productPrice|
	|clare.kavalam@gmail.com|Test*01cv   |Item-3			|T-shirt					 |149.99			|
	|clarelearning@gmail.com|Test*01cg	 |Item-4			|Jeans						 |100.00			|
