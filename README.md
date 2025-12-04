### Requirements

Design a bill sharing app which supports following APIs:

``` 1. /registerUser(String name, String email, String passoword)```
``` /updateUser```

``` 2. /createExpense(User creator, double totalAmount, Map<user, amount> contributions, PaidAmountStrategy paidAmoutStrategy, SplittingStrategy splittingStrategy) ``` 

``` a. splitExpense(splitingStrategy)```
    
```3. /createGroup(String name, List<user>participants) ```

```4. /createGroupExpense(UUID groupId, Map<User, amount> contributions```

```5. /settleExpense()```

```6. /showAllExpense(User user)```

```7. /showOwnedAmount(User user)```

