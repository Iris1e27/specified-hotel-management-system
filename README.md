# SimpleHMS
A database coursework to complete some specific functions about simple hotel management system.

## Brief requirement description
1.Room Booking Requirements with guest and staff mode.

2.Food Service Requirements.


## User manual
### Guest part
1.If you log in this system as a guest, you can use 3 default accounts.
|#User|Username|Password|
|----|--------|--------|
|User1|u001|001|
|User2|u002|002|
|User3|u003|003|

2.Guests can first choose 3 operations.
|Operations|
|----------|
|1. Log in.|
|2. Register.|
|3. Log out.|

3.Guests can choose 7 specific operations.
|Operations|
|----------|
|1. Update self information.|
|2. Check self room records.|
|3. Book a room.|
|4. Cancel a room.|
|5. Check self meal records.|
|6. Book a meal.|
|7. Cancel a meal.|
|quit.|

### Staff part
1.If you log in this system as a staff, you can use 1 default account.
|Username|Password|
|--------|--------|
|admin|000|

2.Staffs can first choose 3 operations.
|Operations|
|----------|
|1. Log in.|
|2. Register.|
|3. Log out.|

3.Staffs can choose 2 specific operations.
|Operations|
|----------|
|1. Update self information.|
|2. Check room states for now and future.|
|quit.|

## Program design
- Use java and mysql.
- For the reasonable screening of most illegal input, the code uses 
a large number of do…while statements. 
- Design 2 classes: staff and guest, when you login with one 
identity the system will create a real object to do.
- Design 2 classes: chef and room to operate database so that 
main() function only focus on the real logic.
- Clear instructions, detailed logical steps and helpful user manual.
