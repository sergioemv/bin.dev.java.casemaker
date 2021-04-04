<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:xalan="http://xml.apache.org/xslt">


	<xsl:template match="/">
		<html>
			<head>
				<title>CaseMaker-Result DependencyCombinations</title>
			</head>
			<body>
				<table width="650" border="0">
					<tr>
						<td colspan="1">
							<div align="left">
								<font face="Arial, Helvetica, sans-serif">
									<strong>
										<em>
											<font size="5">Report Dependency and Combinations</font>
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
						<!--	<td colspan="1"><div align="left">
							<font face="Arial, Helvetica, sans-serif"><strong><em>
							<font size="6">CaseMaker 1.1</font></em></strong>
							</font></div>
							</td>-->

					</tr>
				</table>
				<table border="1" cellpadding="0" cellspacing="0" bordercolor="#000000" width="951">
					<xsl:apply-templates />
				</table>
				<table width="656" border="0">
					<tr>
						<td width="650" height="22" colspan="5">
							<font size="2" face="Arial, Helvetica, sans-serif">
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
		<!--<table width="650" border="0">-->
			<tr>
				<td width="120">
					<font face="Arial, Helvetica, sans-serif">
						<strong>Project:</strong>
					</font>
				</td>
				<td width="530" bgcolor="#CCCCCC" colspan="5">
					<xsl:value-of select="." />
				</td>
			</tr>
		<!--</table>-->
	</xsl:template>


	<xsl:template match="TestObjectName">
		<!--<table width="650" border="0">-->
			<tr>
				<td width="120">
					<font face="Arial, Helvetica, sans-serif">
						<strong>Test Object:</strong>
					</font>
				</td>
				<td width="530" bgcolor="#CCCCCC" colspan="5">
					<xsl:value-of select="." />
				</td>
			</tr>
		<!--</table>-->
	</xsl:template>
	<xsl:template match="TestObjectDescription">
		<!--<table width="650" border="0">-->
			<tr>
				<td width="120">
					<font face="Arial, Helvetica, sans-serif">
						<strong>Description:</strong>
					</font>
				</td>
				<td width="530" bgcolor="#CCCCCC" colspan="5">
					<xsl:value-of select="." />
				</td>
			</tr>
		<!--</table>-->
	</xsl:template>


	<xsl:template match="ActualDate">
		<!--<table width="650" border="0">-->
			<tr>
				<td width="120">
					<font face="Arial, Helvetica, sans-serif">
						<strong>Date:</strong>
					</font>
				</td>
				<td width="530" bgcolor="#CCCCCC" colspan="5">
					<xsl:value-of select="." />
				</td>
			</tr>
		<!--</table>-->
	</xsl:template>


	<xsl:template match="UserName">
		<!--<table width="650" border="0">-->
			<tr>
				<td width="120">
					<font face="Arial, Helvetica, sans-serif">
						<strong>User:</strong>
					</font>
				</td>
				<td width="530" bgcolor="#CCCCCC" colspan="5">
					<xsl:value-of select="." />
				</td>
			</tr>
		<!--</table>-->
	</xsl:template>


	<xsl:template match="headerFirstLineDependency">

		<tr bgcolor="#0000ff">
			<td width="172" bgcolor="#000080" align="center">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@name" />
					</strong>
				</font>
			</td>
			<td width="546" bgcolor="#000080" colspan="4">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@description" />
					</strong>
				</font>
			</td>
			<td width="100" bgcolor="#6666FF" align="center">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@dependency" />
					</strong>
				</font>
			</td>
			<xsl:for-each select="./headerCombinationsLine">
				<td width="100" bgcolor="#000080" align="center">
					<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
						<strong>
							<xsl:value-of select="@name" />
						</strong>
					</font>
				</td>
			</xsl:for-each>
		</tr>

	</xsl:template>

	<xsl:template match="headerLineDependency">

		<tr bgcolor="#0000ff">
			<td width="172" bgcolor="#000080" align="center">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@name" />
					</strong>
				</font>
			</td>
			<td width="546" bgcolor="#000080" colspan="4">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@description" />
					</strong>
				</font>
			</td>
			<td width="100" bgcolor="#FFFFFF" align="center">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@dependency" />
					</strong>
				</font>
			</td>
			<xsl:for-each select="./headerCombinationsLine">
				<td width="100" bgcolor="#000080" align="center">
					<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
						<strong>
							<xsl:value-of select="@name" />
						</strong>
					</font>
				</td>
			</xsl:for-each>
		</tr>

	</xsl:template>


	<xsl:template match="elementFirstLineDependency">

		<tr>
			<td width="172">
				<xsl:value-of select="@name" />
			</td>
			<td width="546" colspan="4">
				<xsl:value-of select="@description" />
			</td>
			<td width="52" align="center">
				<xsl:value-of select="@dependency" />
			</td>
			<xsl:for-each select="./lineCombinations">
				<xsl:choose>
					<xsl:when test="@content=''">
						<td width="52" align="center">
							<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
								<strong>
									<xsl:text></xsl:text>
								</strong>
							</font>
						</td>
					</xsl:when>
					<xsl:otherwise>
						<td width="52" align="center">
							<xsl:element name="img">
								<xsl:attribute name="src">
									<xsl:value-of select="@content" />
								</xsl:attribute>
								<xsl:attribute name="width">15</xsl:attribute>
								<xsl:attribute name="height">15</xsl:attribute>
							</xsl:element>
						</td>

					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
		</tr>

	</xsl:template>

	<xsl:template match="elementLineDependency">

		<tr>
			<td width="172">
				<xsl:value-of select="@name" />
			</td>
			<td width="546" colspan="4">
				<xsl:value-of select="@description" />
			</td>
			<td width="52" align="center">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@dependency" />
					</strong>
				</font>
			</td>
			<xsl:for-each select="./lineCombinations">
				<xsl:choose>
					<xsl:when test="@content=''">
						<td width="52" align="center">
							<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
								<strong>
									<xsl:text></xsl:text>
								</strong>
							</font>
						</td>
					</xsl:when>
					<xsl:otherwise>
						<td width="52" align="center">
							<xsl:element name="img">
								<xsl:attribute name="src">
									<xsl:value-of select="@content" />
								</xsl:attribute>
								<xsl:attribute name="width">15</xsl:attribute>
								<xsl:attribute name="height">15</xsl:attribute>
							</xsl:element>
						</td>

					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
		</tr>
	</xsl:template>


	<xsl:template match="headerEquivalenceClass">

		<tr>
			<td width="172">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@emptyCell" />
					</strong>
				</font>
			</td>
			<td width="148" bgcolor="#6666FF">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@equivalenceClass" />
					</strong>
				</font>
			</td>


			<td width="47" bgcolor="#6666FF" height="19">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@equivalenceClassState" />
					</strong>
				</font>

			</td>
			<td width="114" bgcolor="#6666FF">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@equivalenceClassValue" />
					</strong>
				</font>
			</td>
			<td width="135" bgcolor="#6666FF">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@equivalenceClassDescription" />
					</strong>
				</font>
			</td>
			<td width="143" bgcolor="#FFFFFF">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@emptyCell2" />
					</strong>
				</font>
			</td>
			<xsl:for-each select="./lineCombinations">
				<xsl:choose>
					<xsl:when test="@content=''">
						<td width="52" align="center">
							<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
								<strong>
									<xsl:text></xsl:text>
								</strong>
							</font>
						</td>
					</xsl:when>
					<xsl:otherwise>
						<td width="52" align="center">
							<xsl:element name="img">
								<xsl:attribute name="src">
									<xsl:value-of select="@content" />
								</xsl:attribute>
								<xsl:attribute name="width">15</xsl:attribute>
								<xsl:attribute name="height">15</xsl:attribute>
							</xsl:element>
						</td>

					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
		</tr>

	</xsl:template>

	<xsl:template match="lineEquivalenceClass">

		<tr>
			<td width="172">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@emptyCell" />
					</strong>
				</font>

			</td>
			<td width="148" bgcolor="#FFFFFF">
				<font size="2" face="Verdana, Arial, Helvetica, sans-serif">

					<xsl:value-of select="@equivalenceClass" />

				</font>
			</td>


			<td width="47" bgcolor="#FFFFFF" height="19">
				<font size="2" face="Verdana, Arial, Helvetica, sans-serif">

					<xsl:value-of select="@equivalenceClassState" />

				</font>

			</td>
			<td width="114" bgcolor="#FFFFFF">
				<font size="2" face="Verdana, Arial, Helvetica, sans-serif">

					<xsl:value-of select="@equivalenceClassValue" />

				</font>
			</td>
			<td width="135" bgcolor="#FFFFFF">
				<font size="2" face="Verdana, Arial, Helvetica, sans-serif">

					<xsl:value-of select="@equivalenceClassDescription" />

				</font>
			</td>
			<td width="143" bgcolor="#FFFFFF">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">

					<xsl:value-of select="@emptyCell2" />

				</font>
			</td>
			<xsl:for-each select="./lineCombinations">
				<xsl:choose>
					<xsl:when test="@content=''">
						<td width="52" align="center">
							<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
								<strong>
									<xsl:text></xsl:text>
								</strong>
							</font>
						</td>
					</xsl:when>
					<xsl:otherwise>
						<td width="52" align="center">
							<xsl:element name="img">
								<xsl:attribute name="src">
									<xsl:value-of select="@content" />
								</xsl:attribute>
								<xsl:attribute name="width">15</xsl:attribute>
								<xsl:attribute name="height">15</xsl:attribute>
							</xsl:element>
						</td>

					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
		</tr>

	</xsl:template>

	<xsl:template match="emptyLine1">

		<tr>
			<td width="172" align="center">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@cell1" />
					</strong>
				</font>
			</td>
			<td width="148" align="center">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@cell2" />
					</strong>
				</font>
			</td>
			<td width="47" align="center">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@cell3" />
					</strong>
				</font>

			</td>
			<td width="114" align="center">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@cell4" />
					</strong>
				</font>
			</td>
			<td width="135" align="center">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@cell5" />
					</strong>
				</font>
			</td>
			<td width="143" align="center">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@cell6" />
					</strong>
				</font>
			</td>
			<xsl:for-each select="./headerEffectsInCombination">

				<td width="52" bgcolor="#000080" align="center">
					<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
						<strong>
					<xsl:value-of select="@name" />
					</strong>
					</font>

				</td>
			</xsl:for-each>
		</tr>

	</xsl:template>

	<xsl:template match="emptyLine2">

		<tr>
			<td width="172" align="center">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@cell1" />
					</strong>
				</font>
			</td>
			<td width="148" align="center">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@cell2" />
					</strong>
				</font>
			</td>
			<td width="47" align="center">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@cell3" />
					</strong>
				</font>

			</td>
			<td width="114" align="center">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@cell4" />
					</strong>
				</font>
			</td>
			<td width="135" align="center">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@cell5" />
					</strong>
				</font>
			</td>
			<td width="143" align="center">
				<font color="#FFFFFF" size="2" face="Verdana, Arial, Helvetica, sans-serif">
					<strong>
						<xsl:value-of select="@cell6" />
					</strong>
				</font>
			</td>
			<xsl:for-each select="./cellEffectInCombination">
				<td width="52" align="center">
					<xsl:value-of select="@name" />
				</td>
			</xsl:for-each>
		</tr>

	</xsl:template>

</xsl:stylesheet>