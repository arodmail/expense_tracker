/**
 * Cross-browser object to control screen transitions. 
 */
function DateHelper() {

	this.now = new Date();
	this.movingDate = new Date();

	/**
	 * Returns a date representing the beginning of the year.
	 */	
	this.getYearStart=function() {
		return this.movingDate.getFullYear() + "0101";
	};

	/**
	 * Returns a date representing the beginning of the year.
	 */	
	this.getYearEnd=function() {
		return this.movingDate.getFullYear() + "1215";
	};
	
	/**
	 * Returns a date representing the current year minus 'roll' years.
	 */	
	this.getYearStartRoll=function(roll) {
		return this.movingDate.getFullYear() - roll + "0101";
	};

	/**
	 * Returns the month from the given date, adjusted from JavaScript's date object.
	 */			
	this.getMonth=function(date) {
		var month = date.getMonth()+1;
		if (month < 10) month = "0" + month;
		return month;
	};

	/**
	 * Returns the day from the given date, adjusted transaction periods.
	 */				
	this.getDay=function(date) {
		var day = date.getDate();
		if(day >= 15) return 15;
		else return 1;
	};

	/**
	 * Returns a current date formatted with slashes between date parts.
	 */
	this.getCurrentDateSlashes=function() {
        var result = this.now.getFullYear() + "/";
        result += this.getMonth(this.now) + "/";
        result += this.getDay(this.now);
        return result;
	};

	/**
	 * Returns a moving date formatted without slashes or spaces between date parts.
	 */
	this.getMoving=function() {
	    var result = this.movingDate.getFullYear() + "";
	    result += this.getMonth(this.movingDate) + ""; 
	    result += this.getDay(this.movingDate);
	    return result;
	};

	/**
	 * Returns a moving date formatted with slashes between date parts.
	 */	
	this.getMovingDateSlashes=function() {
	    var result = this.movingDate.getFullYear() + "/";
	    result += this.getMonth(this.movingDate) + "/";
	    result += this.getDay(this.movingDate);
	    return result;
	};

}