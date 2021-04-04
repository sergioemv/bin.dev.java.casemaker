<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="2.0" 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xmlns:tes="testcaselist2.data.report.view.bi" 
xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/02/xpath-functions" xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes"> 
 <xsl:output method="html" encoding="UTF-8"/>
 <xsl:template match="/">
 <html>
  <head>
      <title><xsl:value-of select="/tes:ReportTestCases/Title/Name"/></title>
       <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
  </head>
  <body>
      <xsl:apply-templates select="tes:ReportTestCases"/>
  </body>
</html> 
</xsl:template>
 <xsl:template match="tes:ReportTestCases">
  <table width="650" border="0">
    <tr>
      <td colspan="2">
        <div align="left">
           <font face="Arial, Helvetica, sans-serif"><strong><em><font size="5"><xsl:value-of select="./@LabelTCL1"/></font></em></strong></font>
        </div>
      </td>
      <td width="430" rowspan="7" align="right" valign="top"> 
        <div align="right">
           <font face="Arial, Helvetica, sans-serif"><img src="images/cmlogo.jpg" width="400" height="145"/></font>
        </div>
      </td>
    </tr>
   </table> 
  <font face="Arial, Helvetica, sans-serif">      
   <table width="650" border="0">
    <tr>
      <td width="120">
          <strong><xsl:value-of select="./Project/Name/@LabelTCL1"/></strong>
      </td>
      <td width="530" bgcolor="#CCCCCC"><xsl:value-of select="./Project/Name"/>
      </td>
    </tr>
    <tr>
      <td>
        <strong><xsl:value-of select="./TestObject/Name/@LabelTCL1"/></strong>
      </td>
      <td bgcolor="#CCCCCC"><xsl:value-of select="./TestObject/Name"/>
      </td>
    </tr>
    <table width="650" border="0">
      <tr>
        <td width="120">
          <strong><xsl:value-of select="./Date/@LabelTCL1"/></strong>
        </td>
        <td bgcolor="#CCCCCC"><xsl:value-of select="./Date"/>
        </td>
       </tr>
      <tr>
        <td>
          <strong><xsl:value-of select="./UserName/@LabelTCL1"/></strong>
        </td>  
        <td bgcolor="#CCCCCC"><xsl:value-of select="./UserName"/>
        </td> 
      </tr>
      <tr>
        <td></td>
      </tr>
     </table>  
  <xsl:apply-templates select="Element"/>
</table>
  </font>  
</xsl:template>
<xsl:template match="Element">
  <table border="1" cellpadding="0" cellspacing="0" bordercolor="#000000" width="951">
  <xsl:for-each select=".">
     <tr>
      <td width="172" bgcolor="#000080">
        <font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
          <strong><xsl:value-of select="./Name/@LabelTCL1"/></strong>
        </font>
      </td>
      <td width="546" bgcolor="#000080" colspan="5">
        <font size="2" face="Verdana, Arial, Helvetica, sans-serif" color="#FFFFFF">
          <strong><xsl:value-of select="./Description/@LabelTCL1"/></strong>
        </font>
      </td>
      <xsl:for-each select="../TestCase">      
      <td width="100" bgcolor="#000080" align="center">
        <font size="2" face="Verdana, Arial, Helvetica, sans-serif" color="#FFFFFF">
        <strong><xsl:value-of select="./Name"/></strong>
        </font>
      </td>
      </xsl:for-each>      
    </tr>
    <tr>
      <td width="172"><xsl:value-of select="./Name"/>
      </td>
      <td width="546" colspan="5"><xsl:value-of select="./Description"/>
      </td>
      <td width="52" align="center">
      </td>
      <td width="52" align="center"> 
      </td>
      <td width="52" align="center">
      </td>
      <td width="52" align="center"> 
      </td>
    </tr>
    <tr>
      <td width="172">
      </td>
      <td style="border-style: solid" width="148" bgcolor="#2B80A8">
        <font size="2" face="Verdana, Arial, Helvetica, sans-serif" color="#FFFFFF">
           <strong><xsl:value-of select="./EquivalenceClass/Id/@LabelTCL1"/></strong>
        </font>
      </td>
      <td width="47" bgcolor="#2B80A8" style="border-style: solid" height="19">
        <font size="2" face="Verdana, Arial, Helvetica, sans-serif" color="#FFFFFF">
           <strong><xsl:value-of select="./EquivalenceClass/State/@LabelTCL1"/></strong>
        </font>
      </td>  
      <td width="114" bgcolor="#2B80A8">
        <font size="2" face="Verdana, Arial, Helvetica, sans-serif" color="#FFFFFF">
           <strong><xsl:value-of select="./EquivalenceClass/Value/@LabelTCL1"/></strong>
        </font>
      </td>
      <td width="135" bgcolor="#2B80A8">
        <font size="2" face="Verdana, Arial, Helvetica, sans-serif" color="#FFFFFF">
           <strong><xsl:value-of select="./EquivalenceClass/Description/@LabelTCL1"/></strong>
        </font>
      </td>
      <td width="143" bgcolor="#2B80A8">
        <font size="2" face="Verdana, Arial, Helvetica, sans-serif" color="#FFFFFF">
           <strong><xsl:value-of select="./EquivalenceClass/CauseEffects/@LabelTCL1"/></strong>
        </font>
      </td>
      <td width="52" align="center">
      </td>
      <td width="52" align="center">
      </td>
      <td width="52" align="center">
      </td>
      <td width="52" align="center">
      </td>
    </tr>
    <xsl:apply-templates select="EquivalenceClass"/>
  </xsl:for-each>   
 </table>
</xsl:template> 
<xsl:template match="EquivalenceClass">
  <xsl:for-each select=".">
    <tr>
      <td width="172">
      </td>
      <td width="148"><xsl:value-of select="./Id"/>
      </td>
      <td width="47"><xsl:value-of select="./State"/>
      </td>
      <td width="114"><xsl:value-of select="./Value"/>
      </td>
      <td width="135"><xsl:value-of select="./Description"/>
      </td>
      <td width="143">
        <xsl:for-each select="./CauseEffects/CauseEffect/Name">
          <xsl:value-of select="."/><xsl:text>,</xsl:text>
        </xsl:for-each>
      </td>
      <xsl:for-each select="./tes:Assignment">
        <td width="52" align="center"><xsl:value-of select="."/>
        </td>
      </xsl:for-each>      
    </tr>
    </xsl:for-each>    
</xsl:template>    
</xsl:stylesheet>