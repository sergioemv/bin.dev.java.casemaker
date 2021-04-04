<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:xalan="http://xml.apache.org/xslt">
	<xsl:output method="html" encoding="iso-8859-1" />

	<xsl:template match="/">

		<html>
			<head>
				<title>
					<xsl:value-of select="//ReportHeadTitle" />
				</title>
				<meta http-equiv="Content-Type" content="text/html" charset="iso-8859-1" />
			</head>
			<body>
				<table width="650" border="0">
					<tr>
						<td colspan="2">
							<div align="left">
								<font face="Arial, Helvetica, sans-serif">
									<strong>
										<em>
											<font size="5">
												<xsl:value-of select="//ReportTitle" />
											</font>
										</em>
									</strong>
								</font>
							</div>
						</td>
						<td width="430" rowspan="7" align="right" valign="top">
							<div align="right">
								<font face="Arial, Helvetica, sans-serif">
									<xsl:element name="img">
										<xsl:attribute name="src">
											<xsl:value-of select="//HeaderImg" />
										</xsl:attribute>
										<xsl:attribute name="width">400</xsl:attribute>
										<xsl:attribute name="height">145</xsl:attribute>
									</xsl:element>
								</font>
							</div>
						</td>
					</tr>
				</table>
				<hr align="left" width="650" color="#00CC00" />
				<table width="650" border="0">
					<tr>
						<td>
							<font face="Arial, Helvetica, sans-serif">
								<strong>

									<xsl:value-of select="//DateLabel" />
								</strong>
							</font>
						</td>
						<td bgcolor="#CCCCCC">
							<xsl:value-of select="//ActualDate" />
						</td>
					</tr>
					<tr>
						<td>
							<font face="Arial, Helvetica, sans-serif">
								<strong>
									<xsl:value-of select="//ReportUserNameLabel" />
								</strong>
							</font>
						</td>
						<td bgcolor="#CCCCCC">
							<xsl:value-of select="//UserName" />
						</td>
					</tr>
				</table>
				<hr align="left" width="650" color="#00CC00" />
				<xsl:apply-templates />
				<hr align="left" width="650" />
		<table width="656" border="0">
			<tr>
				<td width="650" height="22">
					<font size="2" face="Arial, Helvetica, sans-serif">
						<em>© 2002-2005 Díaz &amp; Hilterscheid Unternehmensberatung GmbH, Berlin (Germany). All Rights Reserved.</em>
					</font>
				</td>
			</tr>
		</table>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="FormatLine">
		<table width="653" border="0">
			<tr>
				<td width="100" bgcolor="CCCCCC">
					
						
							<xsl:value-of select="./@TDName" />
		
					
				</td>
				<td width="100" bgcolor="#CCCCCC">
											
							<xsl:value-of select="./@SName" />
						
				</td>
				<td width="50" bgcolor="#CCCCCC">
						
							<xsl:value-of select="./@SNumber" />
						
				</td>
				<td width="200" bgcolor="#CCCCCC">
						
							<xsl:value-of select="./@SFormat" />
						
				</td>
				<td width="200" bgcolor="#CCCCCC">
						<strong>
							<xsl:value-of select="./@SValue" />
						</strong>
				</td>
			</tr>
		</table>
	</xsl:template>
	<xsl:template match="TitleFormatLine">
		<table width="653" border="0">
			<tr>
				<td width="100" bgcolor="#000066">
					<font color="#FFFFFF" size="2" face="Arial, Helvetica, sans-serif">
						<strong>
							<xsl:value-of select="./@TDName" />
						</strong>
					</font>
				</td>
				<td width="100" bgcolor="#000066">
					<font color="#FFFFFF" size="2" face="Arial, Helvetica, sans-serif">
						<strong>
							<xsl:value-of select="./@SName" />
						</strong>
					</font>
				</td>
				<td width="50" bgcolor="#000066">
					<font color="#FFFFFF" size="2" face="Arial, Helvetica, sans-serif">
						<strong>
							<xsl:value-of select="./@SNumber" />
						</strong>
					</font>
				</td>
				<td width="200" bgcolor="#000066">
					<font color="#FFFFFF" size="2" face="Arial, Helvetica, sans-serif">
						<strong>
							<xsl:value-of select="./@SFormat" />
						</strong>
					</font>
				</td>
				<td width="200" bgcolor="#000066">
					<font color="#FFFFFF" size="2" face="Arial, Helvetica, sans-serif">
						<strong>
							<xsl:value-of select="./@SValue" />
						</strong>
					</font>
				</td>
			</tr>
		</table>
	</xsl:template>
	<xsl:template match="ReportHeadTitle"></xsl:template>
	<xsl:template match="ReportTitle"></xsl:template>
	<xsl:template match="HeaderImg"></xsl:template>
	<xsl:template match="DateLabel"></xsl:template>
	<xsl:template match="ActualDate"></xsl:template>
	<xsl:template match="ReportUserNameLabel"></xsl:template>
	<xsl:template match="UserName"></xsl:template>
	
</xsl:stylesheet>