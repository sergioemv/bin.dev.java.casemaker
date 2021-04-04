<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="1.0"
    xmlns:xalan="http://xml.apache.org/xslt">
    <xsl:output method="html" encoding="iso-8859-1"/>
    

<xsl:template match="/">
  <html>
    <head>
      <title>CaseMaker-Result Comparison</title>
    </head>
    <body>
    <table width="650" border="0">
    <tr> 
    	<td colspan="1"><div align="left">
    	<font face="Arial, Helvetica, sans-serif"><strong><em>
    	<font size="7">Report Result Comparison</font></em></strong>
    	</font></div>
    	</td>
    	<td width="430" rowspan="7" align="right" valign="top">
    	<div align="right">
    	<font face="Arial, Helvetica, sans-serif">
    	<xsl:element  name="img"> 
    		<xsl:attribute name="src">
    			<xsl:value-of select="//HeaderImg"/>
    		</xsl:attribute> 
    		<xsl:attribute name="width" >
    			400
    		</xsl:attribute>
    		<xsl:attribute name="height" >
    			145
    		</xsl:attribute>
    	</xsl:element>
    	</font>
    	</div>
    	</td>
    <!--	<td colspan="1"><div align="left">
    	<font face="Arial, Helvetica, sans-serif"><strong><em>
    	<font size="6">CaseMaker 1.1</font></em></strong>
    	</font></div>
    	</td>-->

    </tr> 
    </table>
    <hr align="left" width="650" color="#00CC00"/>
    
      <xsl:apply-templates/>
	  <hr align="left" width="650"/>
	  <table width="656" border="0">
	  <tr>
	  <td width="650" height="22"><font size="2" face="Arial, Helvetica, sans-serif">
	  <em>© 2002-2004 Díaz &amp; Hilterscheid Unternehmensberatung GmbH, Berlin (Germany). All Rights Reserved.</em>
	  </font>
	  </td>
	  </tr>
	  </table>
	      </body>
  </html>
</xsl:template>

<xsl:template match="HeaderImg">

</xsl:template>

<xsl:template match="ProjectName">
	<table width="650" border="0">
	<tr>
	<td width="120"><font face="Arial, Helvetica, sans-serif">
	<strong>Project:</strong>
	</font>
	</td>
	<td width="530" bgcolor="#CCCCCC">
		<xsl:value-of select="."/>
	</td>
	</tr>
	</table>
</xsl:template>
<xsl:template match="TestObjectName">
<table width="650" border="0">
	<tr>
	<td width="120"><font face="Arial, Helvetica, sans-serif">
	<strong>Test Object:</strong>
	</font>
	</td>
	<td width="530" bgcolor="#CCCCCC">
		<xsl:value-of select="."/>
	</td>
	</tr>
	</table>
</xsl:template>
<xsl:template match="TestObjectDescription">
<table width="650" border="0">
	<tr>
	<td width="120"><font face="Arial, Helvetica, sans-serif">
	<strong>Description:</strong>
	</font>
	</td>
	<td width="530" bgcolor="#CCCCCC">
		<xsl:value-of select="."/>
	</td>
	</tr>
	</table>
</xsl:template>
<xsl:template match="ActualDate">
<table width="650" border="0">
	<tr>
	<td width="120"><font face="Arial, Helvetica, sans-serif">
	<strong>Date:</strong>
	</font>
	</td>
	<td width="530" bgcolor="#CCCCCC">
		<xsl:value-of select="."/>
	</td>
	</tr>
	</table>
</xsl:template>
<xsl:template match="UserName">
<table width="650" border="0">
	<tr>
	<td width="120"><font face="Arial, Helvetica, sans-serif">
	<strong>User:</strong>
	</font>
	</td>
	<td width="530" bgcolor="#CCCCCC">
		<xsl:value-of select="."/>
	</td>
	</tr>
	</table>
	<hr align="left" width="650" color="#00CC00"/>
	<br/>
	<br/>
</xsl:template>

<xsl:template match="ResultComparisonHeader1">
	<table width="652" border="0">
	<tr bgcolor="#000066"> <td colspan="1">
	<font color="#FFFFFF" size="2" face="Arial, Helvetica, sans-serif">
	<strong>
		<xsl:value-of select="."/> 
	</strong>
	</font>
	</td> 
 	</tr>
 	</table>
</xsl:template>

<xsl:template match="ResultComparisonTestDataHeaderLines">
<br/>
<br/>
<br/>
<hr align="left" width="650" color="#00CC00"/>
	<table width="652" border="0">
	<tr bgcolor="#000033">
	<td colspan="1" bgcolor="#99FF99">
	<font color="#333333" size="2" face="Arial, Helvetica, sans-serif">
	<strong>
		<xsl:value-of select="."/> 
	</strong>
	</font>
	</td> 
 	</tr>
 	</table>
</xsl:template>

<xsl:template match="ResultComparisonStructureHeaderLines">
<hr align="left" width="650" color="#00CC00"/>
	<table width="652" border="0">
	<tr bgcolor="#000033">
	<td colspan="1" bgcolor="#99FF99">
	<font color="#333333" size="2" face="Arial, Helvetica, sans-serif">
	<strong>
		<xsl:value-of select="."/> 
	</strong>
	</font>
	</td> 
 	</tr>
 	</table>
</xsl:template>    

<xsl:template match="ResultComparisonStructureLines">
	<table width="652" border="0">
	<tr bgcolor="#000033">
	<td colspan="1" bgcolor="#CCCCCC">
	<font color="#333333" size="2" face="Arial, Helvetica, sans-serif">
	<strong>
		<xsl:value-of select="."/> 
	</strong>
	</font>
	</td> 
 	</tr>
 	</table>
</xsl:template> 
<xsl:template match="ResultComparisonDetailFoot">
	<table width="652" border="0">
	<tr bgcolor="#000033">
	<td colspan="1" bgcolor="#CCCCCC">
	<font color="#333333" size="2" face="Arial, Helvetica, sans-serif">
	<strong>
		<xsl:value-of select="."/> 
	</strong>
	</font>
	</td> 
 	</tr>
 	</table>
</xsl:template> 
<xsl:template match="ResultComparisonFoot">
	<table width="652" border="0">
	<tr bgcolor="#000066"> <td colspan="1">
	<font color="#FFFFFF" size="2" face="Arial, Helvetica, sans-serif">
	<strong>
		<xsl:value-of select="."/> 
	</strong>
	</font>
	</td> 
 	</tr>
 	</table>
</xsl:template> 
<xsl:template match="ResultComparisonDupla">
<font size="2" face="Arial, Helvetica, sans-serif">Â  </font>

</xsl:template>   
</xsl:stylesheet>
