Feature: Economic Calendar

  Scenario Outline: Events filtering
    Given period <period>, importance <importance> and currency <currency>
    When open the economic calendar
    And filter by period
    And filter by importance
    And filter by currency
    And open the first event
    Then event importance same
    And event country correspond to the currency
    And log event history
    And log event short link

    Examples:
      | period        | importance | currency |
      | CURRENT_MONTH | HIGH       | CHF      |
      | CURRENT_MONTH | HIGH       | CAD      |
      | CURRENT_MONTH | LOW        | CHF      |