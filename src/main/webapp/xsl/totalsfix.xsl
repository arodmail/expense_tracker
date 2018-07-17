<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="html"/>

  <xsl:template match="/rsp">

	<div class="underline">YTD</div>
	
	<table cellpadding="10px" cellspacing="0" width="80%" align="center">
		<tr>
			<td class="tb-cell-right">Rent:</td>
			<td class="tb-cell-left">$<xsl:value-of select="rent"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('rent');">
				(<xsl:value-of select="rent/@count"/>)</a>
			</td>
			<td class="tb-cell-right">Auto Insurance:</td>
			<td class="tb-cell-left">$<xsl:value-of select="autoinsurance"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('autoinsurance');">
				(<xsl:value-of select="autoinsurance/@count"/>)</a>
			</td>
			<td class="tb-cell-right">Cell:</td>
			<td class="tb-cell-left">$<xsl:value-of select="cell"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('cell');">
				(<xsl:value-of select="cell/@count"/>)</a> &#160;
			</td>
		</tr>
		<tr class="tb-row-odd">
			<td class="tb-cell-right">Cable:</td>
			<td class="tb-cell-left">$<xsl:value-of select="cable"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('cable');">
				(<xsl:value-of select="cable/@count"/>)</a>
			</td>
			<td class="tb-cell-right">Credit Card:</td>
			<td class="tb-cell-left">$<xsl:value-of select="creditcard"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('creditcard');">
				(<xsl:value-of select="creditcard/@count"/>)</a>
			</td>
			<td class="tb-cell-right">Student Loan:</td>
			<td class="tb-cell-left">$<xsl:value-of select="studentloan"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('studentloan');">
				(<xsl:value-of select="studentloan/@count"/>)</a>
			</td>
		</tr>
		<tr>
			<td class="tb-cell-right">Net Salary:</td>
			<td class="tb-cell-left">$<xsl:value-of select="netsalary"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('netsalary');">
				(<xsl:value-of select="netsalary/@count"/>)</a>
			</td>
			<td class="tb-cell-right">Utility:</td>
			<td class="tb-cell-left">$<xsl:value-of select="utility"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('utility');">
				(<xsl:value-of select="utility/@count"/>)</a>
			</td>
			<td class="tb-cell-right">Sports:</td>
			<td class="tb-cell-left">$<xsl:value-of select="sports"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('sports');">
				(<xsl:value-of select="sports/@count"/>)</a>
			</td>
		</tr>
		<tr class="tb-row-odd">
			<td class="tb-cell-right">Groceries:</td>
			<td class="tb-cell-left">$<xsl:value-of select="groceries"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('groceries');">
				(<xsl:value-of select="groceries/@count"/>)</a>
			</td>
			<td class="tb-cell-right">Gasoline:</td>
			<td class="tb-cell-left">$<xsl:value-of select="gasoline"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('gasoline');">
				(<xsl:value-of select="gasoline/@count"/>)</a>
			</td>
			<td class="tb-cell-right">Auto:</td>
			<td class="tb-cell-left">$<xsl:value-of select="auto"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('auto');">
				(<xsl:value-of select="auto/@count"/>)</a>
			</td>
		</tr>

	</table>	
  </xsl:template>
  
</xsl:stylesheet>