<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="html"/>

  <xsl:template match="/">

        <table class="tab-set" cellpadding="10" cellspacing="0" width="50%">
          <tr>
	          <td id="tab0" class="tab" onClick="javascript:renderTab(0)" onmouseover="javascript:highlight(this, 'tab-mouse-over');" onmouseout="javascript:restore(this);">
	          Summary
	          </td>

	          <td class="tab-spacer">&#160;</td>

	          <td id="tab1" class="tab" onClick="javascript:renderTab(1)" onmouseover="javascript:highlight(this, 'tab-mouse-over');" onmouseout="javascript:restore(this);">
	          Fixed Totals
	          </td>

	          <td class="tab-spacer">&#160;</td>

	          <td id="tab2" class="tab" onClick="javascript:renderTab(2)" onmouseover="javascript:highlight(this, 'tab-mouse-over');" onmouseout="javascript:restore(this);">
	          Variable Totals
	          </td>

	          <td class="tab-spacer">&#160;</td>

	          <td id="tab3" class="tab" onClick="javascript:renderTab(3)" onmouseover="javascript:highlight(this, 'tab-mouse-over');" onmouseout="javascript:restore(this);">
			    Add
	          </td>

          </tr>
          <tr>
          	<td id="tab-contents" colspan="9" class="tab-content">&#160;</td>
          </tr>
        </table>
        
    </xsl:template>

 </xsl:stylesheet>
