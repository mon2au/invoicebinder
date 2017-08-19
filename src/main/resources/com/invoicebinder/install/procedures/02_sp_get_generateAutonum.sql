DROP procedure IF EXISTS `get_generateAutonum`;
$$

CREATE PROCEDURE `get_generateAutonum` (
	IN in_login VARCHAR(64),
	IN in_password VARCHAR(45),
	OUT out_username VARCHAR(32),
	OUT out_authenticated BOOLEAN
)

MAIN: BEGIN
	DECLARE count INT DEFAULT 0;
	
	-- Default return
	SET out_username = NULL;
	SET out_authenticated = 0;
    
	-- Authenticate using username
	SET @count = (SELECT count(*)
		FROM `user`
		WHERE username =  in_login
		AND password = in_password);
                
	IF (@count = 1) THEN 
		SET out_username = in_login;
		SET out_authenticated = 1;
		LEAVE MAIN;
	END IF;

	-- Authenticate using email
	SET @count = (SELECT count(*)
		FROM `user`, `email`
		WHERE `user`.id = `email`.userID
		AND `email`.`emailAddress` =  in_login
		AND `email`.`isPrimary` = 1
		AND `user`.password = in_password);

	IF (@count = 1) THEN 
		SET out_username = (SELECT username 
			FROM `user`, `email`
			WHERE `user`.id = `email`.userID
			AND `email`.`emailAddress` =  in_login
			AND `email`.`isPrimary` = 1
			AND `user`.password = in_password);
		
		SET out_authenticated = 1;
		LEAVE MAIN;
	END IF;	    
END
$$
