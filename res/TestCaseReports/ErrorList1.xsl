<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:err="errorlist.data.report.view.bi" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/02/xpath-functions" xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes">
	<xsl:output version="1.0" encoding="UTF-8" indent="no" omit-xml-declaration="no" media-type="text/html"/>
	<xsl:template match="/">
		<html>
			<head>
				<title>
					<xsl:value-of select="err:ReportHeadTitle"/>
				</title>
			</head>
			<body>
				<xsl:for-each select="err:ErrorListReport">
					<table width="650" border="0">
						<tbody>
							<tr>
								<xsl:for-each select="err:ReportTitle">
									<td colspan="2">
										<div align="left">
											<font face="Arial, Helvetica, sans-serif">
												<strong>
													<em>
														<font size="5">
															<xsl:apply-templates/>
														</font>
													</em>
												</strong>
											</font>
										</div>
									</td>
								</xsl:for-each>
								<xsl:for-each select="err:HeaderImg">
									<td width="430" rowspan="7" align="right" valign="top">
										<img border="0">
											<xsl:attribute name="src"><xsl:value-of select="."/></xsl:attribute>
											<xsl:attribute name="width">400</xsl:attribute>
											<xsl:attribute name="height">145</xsl:attribute>
										</img>
									</td>
								</xsl:for-each>
							</tr>
						</tbody>
					</table>
					<hr align="left" width="650" color="#00CC00"/>
					<table width="650" border="0">
						<tbody>
							<tr>
								<td width="120">
									<xsl:for-each select="Project">
										<xsl:for-each select="@Label">
											<strong>
												<span style="font-family:Arial; ">
													<xsl:value-of select="."/>
												</span>
											</strong>
										</xsl:for-each>
									</xsl:for-each>
								</td>
								<td width="530" bgcolor="#CCCCCC">
									<xsl:for-each select="Project">
										<xsl:apply-templates/>
									</xsl:for-each>
								</td>
							</tr>
							<tr>
								<td width="120">
									<xsl:for-each select="TestObject">
										<xsl:for-each select="Name">
											<xsl:for-each select="@Label">
												<strong>
													<span style="font-family:Arial; ">
														<xsl:value-of select="."/>
													</span>
												</strong>
											</xsl:for-each>
										</xsl:for-each>
									</xsl:for-each>
								</td>
								<td width="530" bgcolor="#CCCCCC">
									<xsl:for-each select="TestObject">
										<xsl:for-each select="Name">
											<xsl:apply-templates/>
										</xsl:for-each>
									</xsl:for-each>
								</td>
							</tr>
							<tr>
								<td width="120">
									<xsl:for-each select="TestObject">
										<xsl:for-each select="Description">
											<xsl:for-each select="@Label">
												<strong>
													<span style="font-family:Arial; ">
														<xsl:value-of select="."/>
													</span>
												</strong>
											</xsl:for-each>
										</xsl:for-each>
									</xsl:for-each>
								</td>
								<td width="530" bgcolor="#CCCCCC">
									<xsl:for-each select="TestObject">
										<xsl:for-each select="Description">
											<xsl:apply-templates/>
										</xsl:for-each>
									</xsl:for-each>
								</td>
							</tr>
							<tr>
								<td width="120">
									<xsl:for-each select="TestObject">
										<xsl:for-each select="Precondition">
											<xsl:for-each select="@Label">
												<strong>
													<span style="font-family:Arial; ">
														<xsl:value-of select="."/>
													</span>
												</strong>
											</xsl:for-each>
										</xsl:for-each>
									</xsl:for-each>
								</td>
								<td width="530" bgcolor="#CCCCCC">
									<xsl:for-each select="TestObject">
										<xsl:for-each select="Precondition">
											<xsl:apply-templates/>
										</xsl:for-each>
									</xsl:for-each>
								</td>
							</tr>
							<tr>
								<td width="120">
									<xsl:for-each select="Date">
										<xsl:for-each select="@Label">
											<strong>
												<span style="font-family:Arial; ">
													<xsl:value-of select="."/>
												</span>
											</strong>
										</xsl:for-each>
									</xsl:for-each>
								</td>
								<td width="530" bgcolor="#CCCCCC">
									<xsl:for-each select="Date">
										<xsl:apply-templates/>
									</xsl:for-each>
								</td>
							</tr>
							<tr>
								<td width="120">
									<xsl:for-each select="UserName">
										<xsl:for-each select="@Label">
											<strong>
												<span style="font-family:Arial; ">
													<xsl:value-of select="."/>
												</span>
											</strong>
										</xsl:for-each>
									</xsl:for-each>
								</td>
								<td width="530" bgcolor="#CCCCCC">
									<xsl:for-each select="UserName">
										<xsl:apply-templates/>
									</xsl:for-each>
								</td>
							</tr>
						</tbody>
					</table>
					<hr align="left" width="650" color="#00CC00"/>
					<xsl:for-each select="Errors">
						<xsl:for-each select="Error">
							<br/>
							<table width="653" border="0">
								<tbody>
									<tr>
										<td width="183" bgcolor="#000066">
											<xsl:for-each select="Name">
												<xsl:for-each select="@Label">
													<font color="#FFFFFF" size="2" face="Arial, Helvetica, sans-serif">
														<strong>
															<xsl:value-of select="."/>
														</strong>
													</font>
												</xsl:for-each>
											</xsl:for-each>
										</td>
										<td width="460" bgcolor="#CCCCCC">
											<xsl:for-each select="Name">
												<xsl:apply-templates/>
											</xsl:for-each>
										</td>
									</tr>
									<tr>
										<td width="183" bgcolor="#000066">
											<xsl:for-each select="Description">
												<xsl:for-each select="@Label">
													<font color="#FFFFFF" size="2" face="Arial, Helvetica, sans-serif">
														<strong>
															<xsl:value-of select="."/>
														</strong>
													</font>
												</xsl:for-each>
											</xsl:for-each>
										</td>
										<td width="460" bgcolor="#CCCCCC">
											<xsl:for-each select="Description">
												<xsl:apply-templates/>
											</xsl:for-each>
										</td>
									</tr>
									<tr>
										<td width="183" bgcolor="#000066">
											<xsl:for-each select="State">
												<xsl:for-each select="@Label">
													<font color="#FFFFFF" size="2" face="Arial, Helvetica, sans-serif">
														<strong>
															<xsl:value-of select="."/>
														</strong>
													</font>
												</xsl:for-each>
											</xsl:for-each>
										</td>
										<td width="460" bgcolor="#CCCCCC">
											<xsl:for-each select="State">
												<xsl:apply-templates/>
											</xsl:for-each>
										</td>
									</tr>
									<tr>
										<td width="183" bgcolor="#000066">
											<xsl:for-each select="Priority">
												<xsl:for-each select="@Label">
													<font color="#FFFFFF" size="2" face="Arial, Helvetica, sans-serif">
														<strong>
															<xsl:value-of select="."/>
														</strong>
													</font>
												</xsl:for-each>
											</xsl:for-each>
										</td>
										<td width="460" bgcolor="#CCCCCC">
											<xsl:for-each select="Priority">
												<xsl:apply-templates/>
											</xsl:for-each>
										</td>
									</tr>
									<tr>
										<td width="183" bgcolor="#000066">
											<xsl:for-each select="IssueDate">
												<xsl:for-each select="@Label">
													<font color="#FFFFFF" size="2" face="Arial, Helvetica, sans-serif">
														<strong>
															<xsl:value-of select="."/>
														</strong>
													</font>
												</xsl:for-each>
											</xsl:for-each>
										</td>
										<td width="460" bgcolor="#CCCCCC">
											<xsl:for-each select="IssueDate">
												<xsl:apply-templates/>
											</xsl:for-each>
										</td>
									</tr>
									<tr>
										<td width="183" bgcolor="#000066">
											<xsl:for-each select="IssuedBy">
												<xsl:for-each select="@Label">
													<font color="#FFFFFF" size="2" face="Arial, Helvetica, sans-serif">
														<strong>
															<xsl:value-of select="."/>
														</strong>
													</font>
												</xsl:for-each>
											</xsl:for-each>
										</td>
										<td width="460" bgcolor="#CCCCCC">
											<xsl:for-each select="IssuedBy">
												<xsl:apply-templates/>
											</xsl:for-each>
										</td>
									</tr>
									<tr>
										<td width="183" bgcolor="#000066">
											<xsl:for-each select="ClosingDate">
												<xsl:for-each select="@Label">
													<font color="#FFFFFF" size="2" face="Arial, Helvetica, sans-serif">
														<strong>
															<xsl:value-of select="."/>
														</strong>
													</font>
												</xsl:for-each>
											</xsl:for-each>
										</td>
										<td width="460" bgcolor="#CCCCCC">
											<xsl:for-each select="ClosingDate">
												<xsl:apply-templates/>
											</xsl:for-each>
										</td>
									</tr>
									<tr>
										<td width="183" bgcolor="#000066">
											<xsl:for-each select="ClosedBy">
												<xsl:for-each select="@Label">
													<font color="#FFFFFF" size="2" face="Arial, Helvetica, sans-serif">
														<strong>
															<xsl:value-of select="."/>
														</strong>
													</font>
												</xsl:for-each>
											</xsl:for-each>
										</td>
										<td width="460" bgcolor="#CCCCCC">
											<xsl:for-each select="ClosedBy">
												<xsl:apply-templates/>
											</xsl:for-each>
										</td>
									</tr>

								</tbody>
							</table>
							<hr align="left" width="650" color="#00CC00"/>
							<table width="653" border="0">
								<tr bgcolor="#000066">
									<xsl:for-each select="TestCases">
										<td colspan="2">
											<xsl:for-each select="@Label">
												<font color="#FFFFFF" size="2" face="Arial, Helvetica, sans-serif">
													<strong>
														<xsl:value-of select="."/>
													</strong>
												</font>
											</xsl:for-each>
										</td>
										<xsl:for-each select="TestCase">
											<tr bgcolor="#6699FF">
												<xsl:for-each select="Name">
													<xsl:for-each select="@Label">
														<td width="181" bgcolor="#99FF99">
															<font color="#000066" size="2" face="Arial, Helvetica, sans-serif">
																<strong>
																	<xsl:value-of select="."/>
																</strong>
															</font>
														</td>
													</xsl:for-each>
												</xsl:for-each>
												<xsl:for-each select="Description">
													<xsl:for-each select="@Label">
														<td width="462" bgcolor="#99FF99">
															<font color="#000066" size="2" face="Arial, Helvetica, sans-serif">
																<strong>
																	<xsl:value-of select="."/>
																</strong>
															</font>
														</td>
													</xsl:for-each>
												</xsl:for-each>
											</tr>
											<tr>
												<xsl:for-each select="Name">
													<td width="181" bgcolor="#CCCCCC">
														<xsl:value-of select="."/>
													</td>
												</xsl:for-each>
												<xsl:for-each select="Description">
													<td width="462" bgcolor="#CCCCCC">
														<xsl:value-of select="."/>
													</td>
												</xsl:for-each>
											</tr>
										</xsl:for-each>
									</xsl:for-each>
								</tr>
							</table>
							<hr align="left" width="650"/>
							<table width="656" border="0">
								<tr>
									<td width="650" height="22">
										<font size="2" face="Arial, Helvetica, sans-serif">
											<em>© 2002-2005 Díaz &amp; Hilterscheid Unternehmensberatung GmbH, Berlin (Germany). All Rights Reserved.</em>
										</font>
									</td>
								</tr>
							</table>
							<br/>
						</xsl:for-each>
					</xsl:for-each>
					<br/>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
