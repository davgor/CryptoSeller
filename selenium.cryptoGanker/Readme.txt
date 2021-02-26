**************************************************
Crypto exploit hydra

Multilayered program that can buy/sell crypto at a minor drift. Passing unnoticed when purchased, 
generating extremely small profit at each iteration.

Program is split into 4 layers a central brain that manages all sites/sellers/buyers, by analizing the 
data saved by the outer layers
	******** Base Arm  - Default buyer/seller class
	******** Base Eye  - Default observer class
	******** Base Page - default navigation
Each package then extends these for example:
	******** RobinhoodEye
	******** RobinhoodArm
	******** RobinhoodPage

By breaking them down like this I can ensure all classes share the same functions, ensuring identical
behavior even though the websites might interact differently

Normally a non issue but as this is an exploit I will want to have a plan B to begin moving 
to a new site quickly.