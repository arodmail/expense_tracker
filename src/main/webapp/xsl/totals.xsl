<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="html"/>

  <xsl:template match="/rsp">

	<div class="underline">YTD</div>
	
	<table cellpadding="10px" cellspacing="0" width="80%" align="center">
		<tr>
			<td class="tb-cell-right">Savings:</td>
			<td class="tb-cell-left">$<xsl:value-of select="savings"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('savings');">
				(<xsl:value-of select="savings/@count"/>)</a> &#160;
			</td>
			<td class="tb-cell-right">Dining:</td>
			<td class="tb-cell-left">$<xsl:value-of select="dining"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('dining');">
				(<xsl:value-of select="dining/@count"/>)</a>
			</td>
			<td class="tb-cell-right">Adriana:</td>
			<td class="tb-cell-left">$<xsl:value-of select="adriana"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('adriana');">
				(<xsl:value-of select="adriana/@count"/>)</a>
			</td>
		</tr>
		<tr class="tb-row-odd">
			<td class="tb-cell-right">Entertainment:</td>
			<td class="tb-cell-left">$<xsl:value-of select="entertainment"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('entertainment');">
				(<xsl:value-of select="entertainment/@count"/>)</a>
			</td>
			<td class="tb-cell-right">Katherine:</td>
			<td class="tb-cell-left">$<xsl:value-of select="katherine"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('katherine');">
				(<xsl:value-of select="katherine/@count"/>)</a>
			</td>
			<td class="tb-cell-right">Lunches:</td>
			<td class="tb-cell-left">$<xsl:value-of select="lunches"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('lunches');">
				(<xsl:value-of select="lunches/@count"/>)</a>			
			</td>
		</tr>
		<tr>
			<td class="tb-cell-right">Homestore:</td>
			<td class="tb-cell-left">$<xsl:value-of select="homestore"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('homestore');">
				(<xsl:value-of select="homestore/@count"/>)</a>
			</td>
			<td class="tb-cell-right">Personal Care:</td>
			<td class="tb-cell-left">$<xsl:value-of select="personalcare"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('personalcare');">
				(<xsl:value-of select="personalcare/@count"/>)</a>
			</td>
			<td class="tb-cell-right">Medical:</td>
			<td class="tb-cell-left">$<xsl:value-of select="medical"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('medical');">
				(<xsl:value-of select="medical/@count"/>)</a>
			</td>
		</tr>
		<tr class="tb-row-odd">
			<td class="tb-cell-right">Other:</td>
			<td class="tb-cell-left">$<xsl:value-of select="other"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('other');">
				(<xsl:value-of select="other/@count"/>)</a>
			</td>
			<td class="tb-cell-right">Clothes:</td>
			<td class="tb-cell-left">$<xsl:value-of select="clothes"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('clothes');">
				(<xsl:value-of select="clothes/@count"/>)</a>
			</td>
			<td class="tb-cell-right">Snacks:</td>
			<td class="tb-cell-left">$<xsl:value-of select="snacks"/>
				<a href="javascript:void(0);" onclick="javascript:searchByMoving('snacks');">
				(<xsl:value-of select="snacks/@count"/>)</a>
			</td>
		</tr>
		<tr>
		</tr>
	</table>	
  </xsl:template>
  
</xsl:stylesheet>