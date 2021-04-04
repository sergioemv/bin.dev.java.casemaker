<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="1.0"
    xmlns:xalan="http://xml.apache.org/xslt">
 <xsl:output method="html" encoding="UTF-8"/>

<xsl:template match="/">
  <html>
    <head>
      <title>Untitled</title>
    </head>
    <body>

    
      <xsl:apply-templates/>
    </body>
  </html>
</xsl:template>
    <xsl:template match="reportQADirector">
  <table border="1" width="1">
  	<tr>
      <th>Class</th>
      <th>Procedure</th>
      <th>Description</th>
      <th>User Description</th>
  </tr>
    <xsl:for-each select="/reportQADirector/lineReport"> 
      <tr>
      	<td><xsl:value-of select="@nameTestObject"/></td>
      	<td><xsl:value-of select="@nameTestCase"/></td>
        <td><xsl:value-of select="@descriptionTestCase"/></td>   
        <td><xsl:value-of select="@descriptionTestCaseUser"/></td>
      </tr>
    </xsl:for-each>
  </table>
</xsl:template>    
</xsl:stylesheet>
