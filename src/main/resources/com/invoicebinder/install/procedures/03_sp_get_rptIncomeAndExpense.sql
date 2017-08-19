DROP procedure IF EXISTS `get_rptIncomeAndExpense`;
$$

CREATE PROCEDURE `get_rptIncomeAndExpense` (
	IN in_startmonth INTEGER,
	IN in_startyear INTEGER,
	IN in_totalmonths INTEGER
)

BEGIN
	DECLARE v_idx INTEGER DEFAULT 0;
	DECLARE v_month INTEGER DEFAULT in_startmonth;
	DECLARE v_year INTEGER DEFAULT in_startyear;
	
	DROP TEMPORARY TABLE IF EXISTS tblResults;
	CREATE TEMPORARY TABLE IF NOT EXISTS tblResults  (
		invoiceAmt DECIMAL(19,2),
		expenseAmt DECIMAL(19,2),
		monthVal INTEGER,
		yearVal INTEGER
	);


	WHILE v_idx < in_totalmonths DO
		INSERT INTO tblResults 
		SELECT 
			(SELECT COALESCE(sum(invoice.amount),0) FROM invoice 
			WHERE MONTH(invoice.paymentDate) = v_month 
			AND YEAR(invoice.paymentDate) = v_year) as totalincome,

			(SELECT COALESCE(sum(expense.amount),0) FROM expense 
			WHERE MONTH(expense.expenseDate) = v_month 
			AND YEAR(expense.expenseDate) = v_year) as totalexpense,
			v_month as month,
			v_year as year;
			
		-- rollover the month, year
		SET v_month = v_month + 1;
		SET v_idx = v_idx + 1;
		IF v_month = 13 THEN
			SET v_month = 1;
			SET v_year = v_year + 1;
		END IF;
	END WHILE;
	
	SELECT *
	FROM tblResults;
END
$$
