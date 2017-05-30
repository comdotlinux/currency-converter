Please try : https://currency-converter.cfapps.io/    if it is running to directly see the application in action.

Please don't mind the CSS / styling. And the first time please click on the goto converter button from the homepage.

Prerequisites : Good internet connection and a system where you have admin rights (to install docker)

1. Install Docker
	-- If you have Microsoft Windows 10 Professional or Enterprise 64-bit (also need to make some settings)
	-- Then : https://store.docker.com/editions/community/docker-ce-desktop-windows
	-- else : https://www.docker.com/products/docker-toolbox (Easy)
	-- For linux :: respective package managers See https://www.docker.com/ get Docker menu and the Servers section for help.
2. either clone (if you have git or download the latest zip from https://github.com/comdotlinux)
3. unzip and cd to it (on windows please use Docker Quickstrt terminal)
4. Run (from within the project directory -- There is a Dockerfile there)

	i. docker build -t comdotlinux/currencyconverter .
	ii. docker-compose up
5. Once you see the logs settle down, goto your docker host (the ip is different for Docker Toolbox) 
	-- Docker Quickstart terminal run docker-machine ls 
	-- select the ip address from URL column. for me it's 192.168.99.100
6. So either goto localhost or appropriate URL.
7. you should see phpmyadmin
8. enter credentials as : adminqjiBcb3 / 2gp4HtZ1dz6u
9. select currencyconverterdb and click on SQL tab on the right.
10. execute following insert statement
	INSERT INTO `role` VALUES (1,'USERS');COMMIT;
11. goto localhost:8080 or apropriate url:8080 and then once the page loads, click register.
