Feature: User add new address
@address
  Scenario Outline: User creates new address
    Given I am on a main page and I have an address in Addresses
    When I login to already created account
    And I go to the address page
    And I add new address
    And I enter new alias <alias> address <address>  city <city> state <state> zip code <postal code> country <country> phone <phone>
    And I save the new address
    Then I verify created address alias <alias> address <address>  city <city> state <state> zip code <postal code> country <country> phone <phone>
    And I remove the address
    And I can see the address was removed
    And I close the browser
    Examples:
      | alias | address     | city | state   | postal code | country        | phone     |
      | Karol | Cucumber 28 | Ohio | Arizona | 12345       | United Kingdom | 123456789 |


@product
  Scenario Outline: User buys a product
    Given I am on a main page
    When I login to account created in First Task
    And I choose a product
    Then I choose "<size>"
    And I choose "<number>" of product
    And I add this products to a cart
    And I proceed to checkout
    Then I confirm an address
    When I choose delivery method
    And I confirm delivery
    And I choose the payment method
    Then I take a screenshot of confirmation
    And I save total price
    When I go to the order history and details
    Then I check if there is an order
    And I quit the page

    Examples:
      | size | number |
      | M    | 5      |
      | S    | 3      |
      | L    | 2      |

