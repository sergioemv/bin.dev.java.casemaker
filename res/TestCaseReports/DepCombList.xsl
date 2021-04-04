<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:dep="depcomblist.data.report.view.bi" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/02/xpath-functions" xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes">
 <xsl:output method="html" encoding="UTF-8"/>
 <xsl:template match="/">
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title></title>
	</head>
	<body>
      <xsl:apply-templates select="dep:ReportDependency"/>	
	</body>
</html>
</xsl:template>
<xsl:template match="dep:ReportDependency">
		<table border="0" width="650">
			<tr>
				<td colspan="1">
					<div align="left">
						<font face="Arial, Helvetica, sans-serif">
							<strong>
								<em>
									<font size="5"><xsl:value-of select="./@LabelDC"/></font>
								</em>
							</strong>
						</font>
					</div>
				</td>
				<td valign="top" align="right" rowspan="7" width="430">
					<div align="right">
						<font face="Arial, Helvetica, sans-serif">
							<img src="images/cmlogo.jpg" width="400" height="145"/>
						</font>
					</div>
				</td>
			</tr>
		</table>
		<table width="951" bordercolor="#000000" cellspacing="0" cellpadding="0" border="1">
			<tr>
				<td width="120">
					<font face="Arial, Helvetica, sans-serif">
                       <strong><xsl:value-of select="./Project/Name/@LabelDC"/></strong>
					</font>
				</td>
				<td colspan="5" bgcolor="#CCCCCC" width="530">
        			<xsl:value-of select="./Project/Name"/>
                </td>
			</tr>
			<tr>
				<td width="120">
					<font face="Arial, Helvetica, sans-serif">
                       <strong><xsl:value-of select="./TestObject/Name/@LabelDC"/></strong>
					</font>
				</td>
				<td colspan="5" bgcolor="#CCCCCC" width="530">
    				<xsl:value-of select="./TestObject/Name"/>
                </td>
			</tr>
			<tr>
				<td width="120">
					<font face="Arial, Helvetica, sans-serif">
                       <strong><xsl:value-of select="./TestObject/Description/@LabelDC"/></strong>
					</font>
				</td>
				<td colspan="5" bgcolor="#CCCCCC" width="530"> 
    				<xsl:value-of select="./TestObject/Description"/>				
                </td>
			</tr>
			<tr>
				<td width="120">
					<font face="Arial, Helvetica, sans-serif">
                      <strong><xsl:value-of select="./Date/@LabelDC"/></strong>
					</font>
				</td>
				<td colspan="5" bgcolor="#CCCCCC" width="530">
                  <xsl:value-of select="./Date"/>				
                </td>
			</tr>
			<tr>
				<td width="120">
					<font face="Arial, Helvetica, sans-serif">
                       <strong><xsl:value-of select="./UserName/@LabelDC"/></strong>
					</font>
				</td>
				<td colspan="5" bgcolor="#CCCCCC" width="530">
                  <xsl:value-of select="./UserName"/>				
                </td>
			</tr>
           <xsl:apply-templates select="Element"/>			

			<tr>
				<td align="center" width="172">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong> </strong>
					</font>
				</td>
				<td align="center" width="148">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong> </strong>
					</font>
				</td>
				<td align="center" width="47">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong> </strong>
					</font>
				</td>
				<td align="center" width="114">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong> </strong>
					</font>
				</td>
				<td align="center" width="135">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong> </strong>
					</font>
				</td>
				<td align="center" width="143">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong> </strong>
					</font>
				</td>
				<xsl:for-each select="//Combination">
				<td align="center" bgcolor="#000080" width="52">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong>CE</strong>
					</font>
				</td>
               </xsl:for-each>								
			</tr>
			<tr>
				<td align="center" width="172">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong></strong>
					</font>
				</td>
				<td align="center" width="148">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong></strong>
					</font>
				</td>
				<td align="center" width="47">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong></strong>
					</font>
				</td>
				<td align="center" width="114">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong></strong>
					</font>
				</td>
				<td align="center" width="135">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong></strong>
					</font>
				</td>
				<td align="center" width="143">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong></strong>
					</font>
				</td>
				<xsl:for-each select="//Combination">
				<td align="center" width="52">
				  <xsl:for-each select="./CauseEffects/CauseEffect">
				    <xsl:value-of select="./Name"/>,
                  </xsl:for-each>				  
                </td>
                </xsl:for-each>                
			</tr>
		</table>
		<table border="0" width="656">
			<tr>
				<td colspan="5" height="22" width="650">
					<font face="Arial, Helvetica, sans-serif" size="2">
						<em>© 2002-2005 Díaz &amp; Hilterscheid Unternehmensberatung GmbH, Berlin (Germany). All Rights Reserved.</em>
					</font>
				</td>
			</tr>
		</table>
</xsl:template>
<xsl:template match="Element">
  <xsl:for-each select=".">
			<tr bgcolor="#0000ff">
				<td align="center" bgcolor="#000080" width="172">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
                      <strong><xsl:value-of select="./Name/@LabelDC"/></strong>
					</font>
				</td>
				<td colspan="4" bgcolor="#000080" width="546">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
                         <strong><xsl:value-of select="./Description/@LabelDC"/></strong>
					</font>
				</td>
				<xsl:choose>
					<xsl:when test="0=./Id">
         				<td align="center" bgcolor="#6666FF" width="100">
     	       		    	<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
	                            <strong><xsl:value-of select="../Dependency/@LabelDC"/></strong>
             				</font>
			         	</td>
					</xsl:when>
					<xsl:when test="0!=./Id">
         				<td align="center" bgcolor="#FFFFFF" width="100">
     	       		    	<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
	                            <strong></strong>
             				</font>
			         	</td>					
					</xsl:when>
				</xsl:choose>
             <xsl:for-each select="//Combination">      
				<td align="center" bgcolor="#000080" width="100">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong><xsl:value-of select="./Name"/></strong>
					</font>
				</td>
       		</xsl:for-each>
			</tr>
			<tr>
				<td width="172"><xsl:value-of select="./Name"/>
                </td>
				<td colspan="4" width="546"><xsl:value-of select="./Description"/>
                </td>
				<xsl:choose>
					<xsl:when test="0=./Id">
         				<td align="center" width="100">
	                            <xsl:value-of select="../Dependency/Name"/>
			         	</td>
					</xsl:when>
					<xsl:when test="0!=./Id">
         				<td align="center" bgcolor="#FFFFFF" width="100">
     	       		    	<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
	                            <strong></strong>
             				</font>
			         	</td>					
					</xsl:when>
				</xsl:choose>
				<td align="center" width="52">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong/>
					</font>
				</td>
				<td align="center" width="52">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong/>
					</font>
				</td>
				<td align="center" width="52">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong/>
					</font>
				</td>
				<td align="center" width="52">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong/>
					</font>
				</td>
			</tr>
			<tr>
				<td width="172">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong/>
					</font>
				</td>
				<td bgcolor="#6666FF" width="148">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong><xsl:value-of select="./EquivalenceClass/Id/@LabelDC"/></strong>
					</font>
				</td>
				<td height="19" bgcolor="#6666FF" width="47">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong><xsl:value-of select="./EquivalenceClass/State/@LabelDC"/></strong>
					</font>
				</td>
				<td bgcolor="#6666FF" width="114">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong><xsl:value-of select="./EquivalenceClass/Value/@LabelDC"/></strong>
					</font>
				</td>
				<td bgcolor="#6666FF" width="135">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong><xsl:value-of select="./EquivalenceClass/Description/@LabelDC"/></strong>
					</font>
				</td>
				<td bgcolor="#FFFFFF" width="143">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong/>
					</font>
				</td>
				<td align="center" width="52">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong/>
					</font>
				</td>
				<td align="center" width="52">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong/>
					</font>
				</td>
				<td align="center" width="52">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong/>
					</font>
				</td>
				<td align="center" width="52">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong/>
					</font>
				</td>
			</tr>
            <xsl:apply-templates select="EquivalenceClass"/>			
</xsl:for-each>			
</xsl:template>
<xsl:template match="EquivalenceClass">
  <xsl:for-each select=".">
			<tr>
				<td width="172">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF">
						<strong/>
					</font>
				</td>
				<td bgcolor="#FFFFFF" width="148">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2"><xsl:value-of select="./Id"/></font>
				</td>
				<td height="19" bgcolor="#FFFFFF" width="47">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2"><xsl:value-of select="./State"/></font>
				</td>
				<td bgcolor="#FFFFFF" width="114">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2"><xsl:value-of select="./Value"/></font>
				</td>
				<td bgcolor="#FFFFFF" width="135">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2"><xsl:value-of select="./Description"/></font>
				</td>
				<td bgcolor="#FFFFFF" width="143">
					<font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF"/>
				</td>
				<xsl:variable name="EquivalenceClassId">
   				  <xsl:value-of select="./@UniqueId"/>
				</xsl:variable>
				<xsl:for-each select="//Combination">
				  <td align="center" width="52">
				     <xsl:for-each select="./EqClassReference">
 				        <xsl:if test="$EquivalenceClassId=./@UniqueId">
  			 	         
			 		       <img src="images/bola.gif" width="15" height="15"/>
                        </xsl:if>					  
                     </xsl:for-each>                        
  				  </td>
                </xsl:for-each>				
			</tr>
  </xsl:for-each>			
</xsl:template>
</xsl:stylesheet>