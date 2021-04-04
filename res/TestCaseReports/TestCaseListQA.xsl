<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.0" 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xmlns:tes="testcaselist2.data.report.view.bi" 
xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/02/xpath-functions" xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes"> 
 <xsl:output method="html" encoding="UTF-8"/>
<xsl:template match="/">
  <html>
    <head>
      <title>
          <xsl:value-of select="/tes:ReportTestCases/@LabelQA"/>
      </title>
    </head>
    <body>
         <xsl:apply-templates/>          
    </body>
  </html>
</xsl:template>
<xsl:template match="tes:ReportTestCases">
      <table border="1" width="1">
          <tr>
              <th><xsl:value-of select="./TestObject/Name/@LabelQA"/></th>
              <th><xsl:value-of select="./TestCase/Name/@LabelQA"/></th>
              <th><xsl:value-of select="./TestCase/Description/@LabelQA"/></th>
              <th><xsl:value-of select="./TestCase/UserDescription/@LabelQA"/></th>
          </tr>
       <xsl:for-each select="/tes:ReportTestCases/TestCase">
          <tr>
              <td>
              <xsl:if test = "1=./Id">
              <xsl:value-of select="/tes:ReportTestCases/TestObject/Name"/>
              </xsl:if>                               
             </td>              
              <td> <xsl:value-of select="./Name"/></td>
              <td><xsl:value-of select="./Description"/></td>
              <td><xsl:value-of select="./UserDescription"/></td>   
          </tr>
          </xsl:for-each>			
    </table>       
</xsl:template>    
</xsl:stylesheet>
