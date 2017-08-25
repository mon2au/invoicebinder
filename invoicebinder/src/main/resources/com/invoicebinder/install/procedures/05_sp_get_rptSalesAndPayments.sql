DROP procedure IF EXISTS `get_rptSalesAndPayments`;
$$

CREATE PROCEDURE `get_rptSalesAndPayments` (
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
		salesAmt DECIMAL(19,2),
		paidAmt DECIMAL(19,2),
		monthVal INTEGER,
		yearVal INTEGER
	);


	WHILE v_idx < in_totalmonths DO
		INSERT INTO tblResults 
		SELECT 
			(SELECT COALESCE(sum(invoice.amount),0) FROM invoice 
			WHERE MONTH(invoice.invoiceDate) = v_month 
			AND YEAR(invoice.invoiceDate) = v_year) as totalsales,

			(SELECT COALESCE(sum(invoice.amount),0) FROM invoice 
			WHERE MONTH(invoice.invoiceDate) = v_month 
			AND YEAR(invoice.invoiceDate) = v_year
			AND invoice.invoiceStatus = 'PAID') as totalpaid,
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
