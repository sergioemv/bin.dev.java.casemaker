<?xml version="1.0" encoding="ISO-8859-1"?>
<ruleset name="customrulemodel$45$rules" className="ilog.rules.engine.IlrContext" xmlns="http://www.ilog.com/products/xml/schemas/xrl_2.0">
 <rulesetParameters>
  <argument modifier="3" type="loan.Borrower" name="borrower">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="7" type="loan.Loan" name="loan">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="7" type="loan.Report" name="report">
   <constant type="null" value="null"/>
  </argument>
 </rulesetParameters>
 <default-package pkgName="">
  <propertyDefinitions>
   <propertyType name="priority" className="java.lang.String"/>
   <propertyType name="effectiveDate" className="java.util.Date"/>
   <propertyType name="expirationDate" className="java.util.Date"/>
   <propertyType name="status" className="java.lang.String"/>
   <propertyType name="ilog.rules.package" className="ilog.rules.PackageHierarchy"/>
  </propertyDefinitions>
  <packageVariables>
  </packageVariables>
  <flowtask name="loanvalidation">
   <properties>
    <property name="mainflowtask">
     <constant type="string" value="true"/>
    </property>
   </properties>
   <finalActions>
    <block>
     <method name="println">
      <field name="out">
       <variable name="context"/>
      </field>
      <arguments>
       <variable name="borrower"/>
      </arguments>
     </method>
     <method name="println">
      <field name="out">
       <variable name="context"/>
      </field>
      <arguments>
       <variable name="loan"/>
      </arguments>
     </method>
     <method name="println">
      <field name="out">
       <variable name="context"/>
      </field>
      <arguments>
       <variable name="report"/>
      </arguments>
     </method>
    </block>
   </finalActions>
   <body>
    <taskInstance taskname="loanvalidation#initResult" name="null"/>
    <taskInstance taskname="loanvalidation#validation" name="null"/>
    <if name="null">
     <field name="validData">
      <variable name="report"/>
     </field>
     <branch>
      <taskInstance taskname="loanvalidation#computation" name="null"/>
      <taskInstance taskname="loanvalidation#eligibility" name="null"/>
      <if name="null">
       <field name="approved">
        <variable name="report"/>
       </field>
       <branch>
        <taskInstance taskname="loanvalidation#insurance" name="null"/>
       </branch>
      </if>
     </branch>
    </if>
   </body>
  </flowtask>
  <ruletask name="loanvalidation#computation">
   <rulesFiring firing="1" ordering="0" firingLimit="0" algorithm="0"/>
   <dynamicBody value="false"/>
   <body>
    <package pkgName="computation"/>
   </body>
  </ruletask>
  <ruletask name="loanvalidation#insurance">
   <rulesFiring firing="1" ordering="0" firingLimit="0" algorithm="0"/>
   <dynamicBody value="false"/>
   <body>
    <package pkgName="insurance"/>
   </body>
  </ruletask>
  <functiontask name="loanvalidation#initResult">
   <body>
    <block>
     <assign name="=">
      <variable name="report"/>
      <new className="loan.Report">
       <arguments>
        <variable name="borrower"/>
        <variable name="loan"/>
       </arguments>
      </new>
     </assign>
     <assign name="=">
      <field name="approved">
       <variable name="report"/>
      </field>
      <constant type="boolean" value="true"/>
     </assign>
    </block>
   </body>
  </functiontask>
  <ruletask name="loanvalidation#validation">
   <rulesFiring firing="1" ordering="0" firingLimit="0" algorithm="0"/>
   <dynamicBody value="false"/>
   <body>
    <package pkgName="validation"/>
    <package pkgName="validation.borrower"/>
    <package pkgName="validation.loan"/>
   </body>
  </ruletask>
  <ruletask name="loanvalidation#eligibility">
   <rulesFiring firing="1" ordering="0" firingLimit="0" algorithm="0"/>
   <dynamicBody value="false"/>
   <body>
    <package pkgName="eligibility"/>
   </body>
  </ruletask>
 </default-package>
 <package pkgName="translation.loan.LoanUtil">
  <packageVariables>
  </packageVariables>
  <function name="getStringSize" type="int">
   <argument name="s" type="java.lang.String"/>
   <block>
    <return>
     <method name="length">
      <variable name="s"/>
     </method>
    </return>
   </block>
  </function>
  <function name="formattedNumber" type="java.lang.String">
   <argument name="integer" type="int"/>
   <block>
    <return>
     <method name="toString">
      <class className="java.lang.Integer"/>
      <arguments>
       <variable name="integer"/>
      </arguments>
     </method>
    </return>
   </block>
  </function>
 </package>
 <package pkgName="translation.loan.Report">
  <packageVariables>
  </packageVariables>
  <function name="rejectData" type="void">
   <argument name="this" type="loan.Report"/>
   <argument name="message" type="java.lang.String"/>
   <block>
    <method name="setValidData">
     <variable name="this"/>
     <arguments>
      <constant type="boolean" value="false"/>
     </arguments>
    </method>
    <method name="addMessage">
     <variable name="this"/>
     <arguments>
      <variable name="message"/>
     </arguments>
    </method>
   </block>
  </function>
  <function name="approveLoan" type="void">
   <argument name="this" type="loan.Report"/>
   <argument name="message" type="java.lang.String"/>
   <block>
    <method name="setApproved">
     <variable name="this"/>
     <arguments>
      <constant type="boolean" value="true"/>
     </arguments>
    </method>
    <method name="addMessage">
     <variable name="this"/>
     <arguments>
      <variable name="message"/>
     </arguments>
    </method>
   </block>
  </function>
  <function name="rejectLoan" type="void">
   <argument name="this" type="loan.Report"/>
   <argument name="message" type="java.lang.String"/>
   <block>
    <method name="setApproved">
     <variable name="this"/>
     <arguments>
      <constant type="boolean" value="false"/>
     </arguments>
    </method>
    <method name="addMessage">
     <variable name="this"/>
     <arguments>
      <variable name="message"/>
     </arguments>
    </method>
   </block>
  </function>
 </package>
 <package pkgName="validation">
  <packageVariables>
  </packageVariables>
 </package>
 <package pkgName="computation">
  <packageVariables>
  </packageVariables>
  <rule name="neverBankruptcy">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.neverBankruptcy&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <unaryOp name="!">
      <method name="hasLatestBankrupcy">
       <variable name="borrower"/>
      </method>
     </unaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addCorporateScore">
     <variable name="report"/>
     <arguments>
      <constant type="int" value="20"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="salary2score_0">
   <comment>
 * f
 </comment>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="lt">
      <field name="yearlyIncome">
       <variable name="borrower"/>
      </field>
      <constant type="int" value="10000"/>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addCorporateScore">
     <variable name="report"/>
     <arguments>
      <constant type="int" value="21"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="salary2score_1">
   <comment>
 * f
 </comment>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="in">
      <field name="yearlyIncome">
       <variable name="borrower"/>
      </field>
      <interval>
       <leftLimit value="false"/>
       <rightLimit value="true"/>
       <constant type="int" value="10000"/>
       <constant type="int" value="20000"/>
      </interval>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addCorporateScore">
     <variable name="report"/>
     <arguments>
      <constant type="int" value="50"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="salary2score_2">
   <comment>
 * f
 </comment>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="in">
      <field name="yearlyIncome">
       <variable name="borrower"/>
      </field>
      <interval>
       <leftLimit value="false"/>
       <rightLimit value="true"/>
       <constant type="int" value="20000"/>
       <constant type="int" value="30000"/>
      </interval>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addCorporateScore">
     <variable name="report"/>
     <arguments>
      <constant type="int" value="80"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="salary2score_3">
   <comment>
 * f
 </comment>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="in">
      <field name="yearlyIncome">
       <variable name="borrower"/>
      </field>
      <interval>
       <leftLimit value="false"/>
       <rightLimit value="true"/>
       <constant type="int" value="30000"/>
       <constant type="int" value="50000"/>
      </interval>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addCorporateScore">
     <variable name="report"/>
     <arguments>
      <constant type="int" value="120"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="salary2score_4">
   <comment>
 * f
 </comment>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="in">
      <field name="yearlyIncome">
       <variable name="borrower"/>
      </field>
      <interval>
       <leftLimit value="false"/>
       <rightLimit value="true"/>
       <constant type="int" value="50000"/>
       <constant type="int" value="80000"/>
      </interval>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addCorporateScore">
     <variable name="report"/>
     <arguments>
      <constant type="int" value="150"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="salary2score_5">
   <comment>
 * f
 </comment>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="in">
      <field name="yearlyIncome">
       <variable name="borrower"/>
      </field>
      <interval>
       <leftLimit value="false"/>
       <rightLimit value="true"/>
       <constant type="int" value="80000"/>
       <constant type="int" value="120000"/>
      </interval>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addCorporateScore">
     <variable name="report"/>
     <arguments>
      <constant type="int" value="200"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="salary2score_6">
   <comment>
 * f
 </comment>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="in">
      <field name="yearlyIncome">
       <variable name="borrower"/>
      </field>
      <interval>
       <leftLimit value="false"/>
       <rightLimit value="true"/>
       <constant type="int" value="120000"/>
       <constant type="int" value="200000"/>
      </interval>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addCorporateScore">
     <variable name="report"/>
     <arguments>
      <constant type="int" value="250"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="salary2score_7">
   <comment>
 * f
 </comment>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="gte">
      <field name="yearlyIncome">
       <variable name="borrower"/>
      </field>
      <constant type="int" value="200000"/>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addCorporateScore">
     <variable name="report"/>
     <arguments>
      <constant type="int" value="300"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="rate_0">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="lt">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <constant type="int" value="5"/>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <cast type="double">
         <constant type="int" value="0"/>
        </cast>
        <constant type="double" value="0.7"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.05"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_1">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="lt">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <constant type="int" value="5"/>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="double" value="0.7"/>
        <constant type="double" value="0.8"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.052"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_2">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="lt">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <constant type="int" value="5"/>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="double" value="0.8"/>
        <constant type="double" value="0.9"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.053"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_3">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="lt">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <constant type="int" value="5"/>
      </binaryOp>
      <binaryOp name="gte">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <constant type="double" value="0.9"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.055"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_4">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="5"/>
        <constant type="int" value="8"/>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <cast type="double">
         <constant type="int" value="0"/>
        </cast>
        <constant type="double" value="0.7"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.056"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_5">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="5"/>
        <constant type="int" value="8"/>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="double" value="0.7"/>
        <constant type="double" value="0.8"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.057"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_6">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="5"/>
        <constant type="int" value="8"/>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="double" value="0.8"/>
        <constant type="double" value="0.9"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.058"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_7">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="5"/>
        <constant type="int" value="8"/>
       </interval>
      </binaryOp>
      <binaryOp name="gte">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <constant type="double" value="0.9"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.059"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_8">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="9"/>
        <constant type="int" value="12"/>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <cast type="double">
         <constant type="int" value="0"/>
        </cast>
        <constant type="double" value="0.7"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.06"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_9">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="9"/>
        <constant type="int" value="12"/>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="double" value="0.7"/>
        <constant type="double" value="0.8"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.061"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_10">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="9"/>
        <constant type="int" value="12"/>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="double" value="0.8"/>
        <constant type="double" value="0.9"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.062"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_11">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="9"/>
        <constant type="int" value="12"/>
       </interval>
      </binaryOp>
      <binaryOp name="gte">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <constant type="double" value="0.9"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.063"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_12">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="13"/>
        <constant type="int" value="17"/>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <cast type="double">
         <constant type="int" value="0"/>
        </cast>
        <constant type="double" value="0.7"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.064"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_13">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="13"/>
        <constant type="int" value="17"/>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="double" value="0.7"/>
        <constant type="double" value="0.8"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.065"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_14">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="13"/>
        <constant type="int" value="17"/>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="double" value="0.8"/>
        <constant type="double" value="0.9"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.066"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_15">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="13"/>
        <constant type="int" value="17"/>
       </interval>
      </binaryOp>
      <binaryOp name="gte">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <constant type="double" value="0.9"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.067"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_16">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="18"/>
        <constant type="int" value="25"/>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <cast type="double">
         <constant type="int" value="0"/>
        </cast>
        <constant type="double" value="0.7"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.068"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_17">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="18"/>
        <constant type="int" value="25"/>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="double" value="0.7"/>
        <constant type="double" value="0.8"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.069"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_18">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="18"/>
        <constant type="int" value="25"/>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="double" value="0.8"/>
        <constant type="double" value="0.9"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.07"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_19">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="18"/>
        <constant type="int" value="25"/>
       </interval>
      </binaryOp>
      <binaryOp name="gte">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <constant type="double" value="0.9"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.08"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_20">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="gte">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <constant type="int" value="26"/>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <cast type="double">
         <constant type="int" value="0"/>
        </cast>
        <constant type="double" value="0.7"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.081"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_21">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="gte">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <constant type="int" value="26"/>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="double" value="0.7"/>
        <constant type="double" value="0.8"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.082"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_22">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="gte">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <constant type="int" value="26"/>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="double" value="0.8"/>
        <constant type="double" value="0.9"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.083"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="rate_23">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.rate&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="gte">
       <field name="duration">
        <variable name="loan"/>
       </field>
       <constant type="int" value="26"/>
      </binaryOp>
      <binaryOp name="gte">
       <field name="loanToValue">
        <variable name="loan"/>
       </field>
       <constant type="double" value="0.9"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="loan"/>
     </field>
     <constant type="double" value="0.085"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="initialCorporateScore">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="expirationDate">
     <constant type="string" value="java.util.Date(&quot;11&#47;24&#47;2005 10:47&quot;)"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.initialCorporateScore&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="ilog.rules.engine.IlrContext">
     <enumerator name="from">
      <variable name="context"/>
     </enumerator>
    </simpleCondition>
   </when>
   <then>
    <assign name="=">
     <field name="corporateScore">
      <variable name="report"/>
     </field>
     <field name="creditScore">
      <variable name="borrower"/>
     </field>
    </assign>
   </then>
  </rule>
  <rule name="bankruptcyScore_0">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.bankruptcyScore&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.bankruptcyScore&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <method name="hasLatestBankrupcy">
       <variable name="borrower"/>
      </method>
      <binaryOp name="in">
       <method name="translation.loan.Borrower.getBankruptcyAge">
        <arguments>
         <variable name="borrower"/>
        </arguments>
       </method>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="0"/>
        <constant type="int" value="1"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addCorporateScore">
     <variable name="report"/>
     <arguments>
      <constant type="int" value="-200"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="bankruptcyScore_1">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.bankruptcyScore&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.bankruptcyScore&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <method name="hasLatestBankrupcy">
       <variable name="borrower"/>
      </method>
      <binaryOp name="in">
       <method name="translation.loan.Borrower.getBankruptcyAge">
        <arguments>
         <variable name="borrower"/>
        </arguments>
       </method>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="2"/>
        <constant type="int" value="5"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addCorporateScore">
     <variable name="report"/>
     <arguments>
      <constant type="int" value="-100"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="bankruptcyScore_2">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.bankruptcyScore&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.bankruptcyScore&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <method name="hasLatestBankrupcy">
       <variable name="borrower"/>
      </method>
      <binaryOp name="in">
       <method name="translation.loan.Borrower.getBankruptcyAge">
        <arguments>
         <variable name="borrower"/>
        </arguments>
       </method>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="6"/>
        <constant type="int" value="8"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addCorporateScore">
     <variable name="report"/>
     <arguments>
      <constant type="int" value="-50"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="bankruptcyScore_3">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.bankruptcyScore&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.bankruptcyScore&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <method name="hasLatestBankrupcy">
       <variable name="borrower"/>
      </method>
      <unaryOp name="!">
       <naryOp name="or">
        <binaryOp name="in">
         <method name="translation.loan.Borrower.getBankruptcyAge">
          <arguments>
           <variable name="borrower"/>
          </arguments>
         </method>
         <interval>
          <leftLimit value="false"/>
          <rightLimit value="false"/>
          <constant type="int" value="0"/>
          <constant type="int" value="1"/>
         </interval>
        </binaryOp>
        <binaryOp name="in">
         <method name="translation.loan.Borrower.getBankruptcyAge">
          <arguments>
           <variable name="borrower"/>
          </arguments>
         </method>
         <interval>
          <leftLimit value="false"/>
          <rightLimit value="false"/>
          <constant type="int" value="2"/>
          <constant type="int" value="5"/>
         </interval>
        </binaryOp>
        <binaryOp name="in">
         <method name="translation.loan.Borrower.getBankruptcyAge">
          <arguments>
           <variable name="borrower"/>
          </arguments>
         </method>
         <interval>
          <leftLimit value="false"/>
          <rightLimit value="false"/>
          <constant type="int" value="6"/>
          <constant type="int" value="8"/>
         </interval>
        </binaryOp>
       </naryOp>
      </unaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addCorporateScore">
     <variable name="report"/>
     <arguments>
      <constant type="int" value="-10"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="repayment">
   <priority>
    <field name="low">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;computation&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;computation.repayment&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <bind name="amount">
      <field name="amount">
       <variable name="loan"/>
      </field>
     </bind>
    </evaluateCondition>
    <evaluateCondition>
     <bind name="duration">
      <field name="numberOfMonthlyPayments">
       <variable name="loan"/>
      </field>
     </bind>
    </evaluateCondition>
    <evaluateCondition>
     <bind name="rate">
      <field name="yearlyInterestRate">
       <variable name="loan"/>
      </field>
     </bind>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="monthlyRepayment">
      <variable name="loan"/>
     </field>
     <method name="getMonthlyRepayment">
      <class className="loan.LoanUtil"/>
      <arguments>
       <cast type="double">
        <variable name="amount"/>
       </cast>
       <variable name="duration"/>
       <variable name="rate"/>
      </arguments>
     </method>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
 </package>
 <package pkgName="validation.borrower">
  <packageVariables>
  </packageVariables>
  <rule name="checkAge">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;validation.borrower&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;validation.borrower.checkAge&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="ilog.rules.engine.IlrContext">
     <enumerator name="from">
      <variable name="context"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <bind name="minAge">
      <constant type="int" value="0"/>
     </bind>
    </evaluateCondition>
    <evaluateCondition>
     <bind name="maxAge">
      <constant type="int" value="150"/>
     </bind>
    </evaluateCondition>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <unaryOp name="!">
      <binaryOp name="in">
       <method name="translation.loan.Borrower.age">
        <arguments>
         <variable name="borrower"/>
        </arguments>
       </method>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <variable name="minAge"/>
        <variable name="maxAge"/>
       </interval>
      </binaryOp>
     </unaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="translation.loan.Report.rejectData">
     <arguments>
      <variable name="report"/>
      <constant type="string" value="The borrower&apos;s age is not valid."/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="checkSSNdigits">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;validation.borrower&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;validation.borrower.checkSSNdigits&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="ilog.rules.engine.IlrContext">
     <enumerator name="from">
      <variable name="context"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <bind name="digits">
      <constant type="int" value="9"/>
     </bind>
    </evaluateCondition>
    <evaluateCondition>
     <bind name="areanumber">
      <constant type="int" value="3"/>
     </bind>
    </evaluateCondition>
    <evaluateCondition>
     <bind name="groupcode">
      <constant type="int" value="2"/>
     </bind>
    </evaluateCondition>
    <evaluateCondition>
     <bind name="serialnumber">
      <constant type="int" value="4"/>
     </bind>
    </evaluateCondition>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="or">
      <binaryOp name="!=">
       <field name="digits">
        <method name="translation.loan.Borrower.ssn">
         <arguments>
          <variable name="borrower"/>
         </arguments>
        </method>
       </field>
       <variable name="digits"/>
      </binaryOp>
      <binaryOp name="!=">
       <method name="translation.loan.LoanUtil.getStringSize">
        <arguments>
         <field name="areaNumber">
          <method name="translation.loan.Borrower.ssn">
           <arguments>
            <variable name="borrower"/>
           </arguments>
          </method>
         </field>
        </arguments>
       </method>
       <variable name="areanumber"/>
      </binaryOp>
      <unaryOp name="!">
       <method name="containsOnlyDigits">
        <class className="loan.LoanUtil"/>
        <arguments>
         <field name="areaNumber">
          <method name="translation.loan.Borrower.ssn">
           <arguments>
            <variable name="borrower"/>
           </arguments>
          </method>
         </field>
        </arguments>
       </method>
      </unaryOp>
      <binaryOp name="!=">
       <method name="translation.loan.LoanUtil.getStringSize">
        <arguments>
         <field name="groupCode">
          <method name="translation.loan.Borrower.ssn">
           <arguments>
            <variable name="borrower"/>
           </arguments>
          </method>
         </field>
        </arguments>
       </method>
       <variable name="groupcode"/>
      </binaryOp>
      <unaryOp name="!">
       <method name="containsOnlyDigits">
        <class className="loan.LoanUtil"/>
        <arguments>
         <field name="groupCode">
          <method name="translation.loan.Borrower.ssn">
           <arguments>
            <variable name="borrower"/>
           </arguments>
          </method>
         </field>
        </arguments>
       </method>
      </unaryOp>
      <binaryOp name="!=">
       <method name="translation.loan.LoanUtil.getStringSize">
        <arguments>
         <field name="serialNumber">
          <method name="translation.loan.Borrower.ssn">
           <arguments>
            <variable name="borrower"/>
           </arguments>
          </method>
         </field>
        </arguments>
       </method>
       <variable name="serialnumber"/>
      </binaryOp>
      <unaryOp name="!">
       <method name="containsOnlyDigits">
        <class className="loan.LoanUtil"/>
        <arguments>
         <field name="serialNumber">
          <method name="translation.loan.Borrower.ssn">
           <arguments>
            <variable name="borrower"/>
           </arguments>
          </method>
         </field>
        </arguments>
       </method>
      </unaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="translation.loan.Report.rejectData">
     <arguments>
      <variable name="report"/>
      <constant type="string" value="The borrower&apos;s SSN should be formatted with 3-2-4 digits"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="checkSSNareanumber">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;validation.borrower&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;validation.borrower.checkSSNareanumber&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="in">
      <field name="areaNumber">
       <method name="translation.loan.Borrower.ssn">
        <arguments>
         <variable name="borrower"/>
        </arguments>
       </method>
      </field>
      <newArray className="java.lang.Object">
       <realArray value="false"/>
       <dimension value="1"/>
       <initValues>
        <constant type="string" value="000"/>
        <constant type="string" value="666"/>
       </initValues>
      </newArray>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="translation.loan.Report.rejectData">
     <arguments>
      <variable name="report"/>
      <binaryOp name="+">
       <constant type="string" value="The borrower&apos;s SSN area number cannot be "/>
       <field name="areaNumber">
        <method name="translation.loan.Borrower.ssn">
         <arguments>
          <variable name="borrower"/>
         </arguments>
        </method>
       </field>
      </binaryOp>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="checkName">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;validation.borrower&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;validation.borrower.checkName&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="or">
      <binaryOp name="==">
       <field name="lastName">
        <variable name="borrower"/>
       </field>
       <constant type="null" value="null"/>
      </binaryOp>
      <binaryOp name="==">
       <method name="length">
        <field name="lastName">
         <variable name="borrower"/>
        </field>
       </method>
       <constant type="int" value="0"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="translation.loan.Report.rejectData">
     <arguments>
      <variable name="report"/>
      <constant type="string" value="The borrower&apos;s name is empty"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="checkZipcode">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;validation.borrower&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;validation.borrower.checkZipcode&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="ilog.rules.engine.IlrContext">
     <enumerator name="from">
      <variable name="context"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <bind name="zip_length">
      <constant type="int" value="5"/>
     </bind>
    </evaluateCondition>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="!=">
      <method name="translation.loan.LoanUtil.getStringSize">
       <arguments>
        <field name="zipCode">
         <variable name="borrower"/>
        </field>
       </arguments>
      </method>
      <variable name="zip_length"/>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="translation.loan.Report.rejectData">
     <arguments>
      <variable name="report"/>
      <binaryOp name="+">
       <binaryOp name="+">
        <constant type="string" value="The borrower&apos;s Zip Code should have "/>
        <method name="translation.loan.LoanUtil.formattedNumber">
         <arguments>
          <variable name="zip_length"/>
         </arguments>
        </method>
       </binaryOp>
       <constant type="string" value=" digits"/>
      </binaryOp>
     </arguments>
    </method>
   </then>
  </rule>
 </package>
 <package pkgName="insurance">
  <packageVariables>
  </packageVariables>
  <rule name="defaultInsurance">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.defaultInsurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="ilog.rules.engine.IlrContext">
     <enumerator name="from">
      <variable name="context"/>
     </enumerator>
    </simpleCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="report"/>
     </field>
     <constant type="double" value="0.02"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_0">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="report"/>
        </field>
        <arguments>
         <constant type="string" value="A"/>
        </arguments>
       </method>
       <binaryOp name="lt">
        <field name="amount">
         <variable name="loan"/>
        </field>
        <constant type="int" value="100000"/>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="report"/>
     </field>
     <constant type="boolean" value="false"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_1">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="report"/>
        </field>
        <arguments>
         <constant type="string" value="A"/>
        </arguments>
       </method>
       <binaryOp name="in">
        <field name="amount">
         <variable name="loan"/>
        </field>
        <interval>
         <leftLimit value="false"/>
         <rightLimit value="true"/>
         <constant type="int" value="100000"/>
         <constant type="int" value="300000"/>
        </interval>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="report"/>
     </field>
     <constant type="double" value="0.0010"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_2">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="report"/>
        </field>
        <arguments>
         <constant type="string" value="A"/>
        </arguments>
       </method>
       <binaryOp name="in">
        <field name="amount">
         <variable name="loan"/>
        </field>
        <interval>
         <leftLimit value="false"/>
         <rightLimit value="true"/>
         <constant type="int" value="300000"/>
         <constant type="int" value="600000"/>
        </interval>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="report"/>
     </field>
     <constant type="double" value="0.0030"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_3">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="report"/>
        </field>
        <arguments>
         <constant type="string" value="A"/>
        </arguments>
       </method>
       <binaryOp name="gte">
        <field name="amount">
         <variable name="loan"/>
        </field>
        <constant type="int" value="600000"/>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="report"/>
     </field>
     <constant type="double" value="0.0050"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_4">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="report"/>
        </field>
        <arguments>
         <constant type="string" value="B"/>
        </arguments>
       </method>
       <binaryOp name="lt">
        <field name="amount">
         <variable name="loan"/>
        </field>
        <constant type="int" value="100000"/>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="report"/>
     </field>
     <constant type="boolean" value="false"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_5">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="report"/>
        </field>
        <arguments>
         <constant type="string" value="B"/>
        </arguments>
       </method>
       <binaryOp name="in">
        <field name="amount">
         <variable name="loan"/>
        </field>
        <interval>
         <leftLimit value="false"/>
         <rightLimit value="true"/>
         <constant type="int" value="100000"/>
         <constant type="int" value="300000"/>
        </interval>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="report"/>
     </field>
     <constant type="double" value="0.0025"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_6">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="report"/>
        </field>
        <arguments>
         <constant type="string" value="B"/>
        </arguments>
       </method>
       <binaryOp name="in">
        <field name="amount">
         <variable name="loan"/>
        </field>
        <interval>
         <leftLimit value="false"/>
         <rightLimit value="true"/>
         <constant type="int" value="300000"/>
         <constant type="int" value="600000"/>
        </interval>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="report"/>
     </field>
     <constant type="double" value="0.0050"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_7">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="report"/>
        </field>
        <arguments>
         <constant type="string" value="B"/>
        </arguments>
       </method>
       <binaryOp name="gte">
        <field name="amount">
         <variable name="loan"/>
        </field>
        <constant type="int" value="600000"/>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="report"/>
     </field>
     <constant type="double" value="0.0075"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_9">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="report"/>
        </field>
        <arguments>
         <constant type="string" value="C"/>
        </arguments>
       </method>
       <binaryOp name="lt">
        <field name="amount">
         <variable name="loan"/>
        </field>
        <constant type="int" value="100000"/>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="report"/>
     </field>
     <constant type="double" value="0.0035"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_10">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="report"/>
        </field>
        <arguments>
         <constant type="string" value="C"/>
        </arguments>
       </method>
       <binaryOp name="in">
        <field name="amount">
         <variable name="loan"/>
        </field>
        <interval>
         <leftLimit value="false"/>
         <rightLimit value="true"/>
         <constant type="int" value="100000"/>
         <constant type="int" value="300000"/>
        </interval>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="report"/>
     </field>
     <constant type="double" value="0.0060"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_11">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="report"/>
        </field>
        <arguments>
         <constant type="string" value="C"/>
        </arguments>
       </method>
       <binaryOp name="in">
        <field name="amount">
         <variable name="loan"/>
        </field>
        <interval>
         <leftLimit value="false"/>
         <rightLimit value="true"/>
         <constant type="int" value="300000"/>
         <constant type="int" value="600000"/>
        </interval>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="report"/>
     </field>
     <constant type="double" value="0.0085"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_12">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="report"/>
        </field>
        <arguments>
         <constant type="string" value="C"/>
        </arguments>
       </method>
       <binaryOp name="gte">
        <field name="amount">
         <variable name="loan"/>
        </field>
        <constant type="int" value="600000"/>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="report"/>
     </field>
     <constant type="double" value="0.0145"/>
    </assign>
   </then>
  </rule>
 </package>
 <package pkgName="translation.loan.Borrower">
  <packageVariables>
  </packageVariables>
  <function name="getBankruptcyAge" type="int">
   <argument name="this" type="loan.Borrower"/>
   <block>
    <return>
     <method name="getAge">
      <class className="loan.DateUtil"/>
      <arguments>
       <field name="latestBankruptcyDate">
        <variable name="this"/>
       </field>
       <method name="now">
        <class className="loan.DateUtil"/>
       </method>
      </arguments>
     </method>
    </return>
   </block>
  </function>
  <function name="age" type="int">
   <argument name="this" type="loan.Borrower"/>
   <block>
    <return>
     <method name="getAge">
      <class className="loan.DateUtil"/>
      <arguments>
       <method name="getBirthDate">
        <variable name="this"/>
       </method>
       <method name="now">
        <class className="loan.DateUtil"/>
       </method>
      </arguments>
     </method>
    </return>
   </block>
  </function>
  <function name="ssn" type="loan.Borrower.SSN">
   <argument name="this" type="loan.Borrower"/>
   <block>
    <return>
     <method name="getSSN">
      <variable name="this"/>
     </method>
    </return>
   </block>
  </function>
 </package>
 <package pkgName="validation.loan">
  <packageVariables>
  </packageVariables>
  <rule name="checkAmount">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;validation.loan&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;validation.loan.checkAmount&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="ilog.rules.engine.IlrContext">
     <enumerator name="from">
      <variable name="context"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <bind name="maxAmount">
      <constant type="int" value="1000000"/>
     </bind>
    </evaluateCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="gte">
      <field name="amount">
       <variable name="loan"/>
      </field>
      <variable name="maxAmount"/>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="translation.loan.Report.rejectData">
     <arguments>
      <variable name="report"/>
      <binaryOp name="+">
       <constant type="string" value="The loan cannot exceed "/>
       <method name="translation.loan.LoanUtil.formattedNumber">
        <arguments>
         <variable name="maxAmount"/>
        </arguments>
       </method>
      </binaryOp>
     </arguments>
    </method>
   </then>
  </rule>
 </package>
 <package pkgName="eligibility">
  <packageVariables>
  </packageVariables>
  <rule name="grade_0">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;eligibility&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="yearlyRepayment">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <cast type="double">
         <constant type="int" value="0"/>
        </cast>
        <cast type="double">
         <constant type="int" value="10000"/>
        </cast>
       </interval>
      </binaryOp>
      <binaryOp name="gte">
       <field name="corporateScore">
        <variable name="report"/>
       </field>
       <constant type="int" value="900"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="grade">
      <variable name="report"/>
     </field>
     <constant type="string" value="A"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="report"/>
     <arguments>
      <constant type="string" value="Very low risk loan"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="grade_1">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;eligibility&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="yearlyRepayment">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <cast type="double">
         <constant type="int" value="0"/>
        </cast>
        <cast type="double">
         <constant type="int" value="10000"/>
        </cast>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="corporateScore">
        <variable name="report"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="600"/>
        <constant type="int" value="900"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="grade">
      <variable name="report"/>
     </field>
     <constant type="string" value="A"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="report"/>
     <arguments>
      <constant type="string" value="Very low risk loan"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="grade_2">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;eligibility&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="yearlyRepayment">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <cast type="double">
         <constant type="int" value="0"/>
        </cast>
        <cast type="double">
         <constant type="int" value="10000"/>
        </cast>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="corporateScore">
        <variable name="report"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="300"/>
        <constant type="int" value="600"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="grade">
      <variable name="report"/>
     </field>
     <constant type="string" value="B"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="report"/>
     <arguments>
      <constant type="string" value="Low risk loan"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="grade_3">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;eligibility&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="yearlyRepayment">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <cast type="double">
         <constant type="int" value="10000"/>
        </cast>
        <cast type="double">
         <constant type="int" value="30000"/>
        </cast>
       </interval>
      </binaryOp>
      <binaryOp name="gte">
       <field name="corporateScore">
        <variable name="report"/>
       </field>
       <constant type="int" value="900"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="grade">
      <variable name="report"/>
     </field>
     <constant type="string" value="A"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="report"/>
     <arguments>
      <constant type="string" value="Very low risk loan"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="grade_4">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;eligibility&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="yearlyRepayment">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <cast type="double">
         <constant type="int" value="10000"/>
        </cast>
        <cast type="double">
         <constant type="int" value="30000"/>
        </cast>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="corporateScore">
        <variable name="report"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="600"/>
        <constant type="int" value="900"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="grade">
      <variable name="report"/>
     </field>
     <constant type="string" value="B"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="report"/>
     <arguments>
      <constant type="string" value="Low risk loan"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="grade_5">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;eligibility&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="yearlyRepayment">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <cast type="double">
         <constant type="int" value="10000"/>
        </cast>
        <cast type="double">
         <constant type="int" value="30000"/>
        </cast>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="corporateScore">
        <variable name="report"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="300"/>
        <constant type="int" value="600"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="grade">
      <variable name="report"/>
     </field>
     <constant type="string" value="C"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="report"/>
     <arguments>
      <constant type="string" value="Average risk loan"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="grade_6">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;eligibility&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="yearlyRepayment">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <cast type="double">
         <constant type="int" value="30000"/>
        </cast>
        <cast type="double">
         <constant type="int" value="60000"/>
        </cast>
       </interval>
      </binaryOp>
      <binaryOp name="gte">
       <field name="corporateScore">
        <variable name="report"/>
       </field>
       <constant type="int" value="900"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="grade">
      <variable name="report"/>
     </field>
     <constant type="string" value="B"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="report"/>
     <arguments>
      <constant type="string" value="Low risk loan"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="grade_7">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;eligibility&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="yearlyRepayment">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <cast type="double">
         <constant type="int" value="30000"/>
        </cast>
        <cast type="double">
         <constant type="int" value="60000"/>
        </cast>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="corporateScore">
        <variable name="report"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="600"/>
        <constant type="int" value="900"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="grade">
      <variable name="report"/>
     </field>
     <constant type="string" value="C"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="report"/>
     <arguments>
      <constant type="string" value="Average risk loan"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="grade_8">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;eligibility&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="yearlyRepayment">
        <variable name="loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <cast type="double">
         <constant type="int" value="30000"/>
        </cast>
        <cast type="double">
         <constant type="int" value="60000"/>
        </cast>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="corporateScore">
        <variable name="report"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="300"/>
        <constant type="int" value="600"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="grade">
      <variable name="report"/>
     </field>
     <constant type="string" value="D"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="report"/>
     <arguments>
      <constant type="string" value="Risky loan"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="grade_9">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;eligibility&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="gte">
       <field name="yearlyRepayment">
        <variable name="loan"/>
       </field>
       <cast type="double">
        <constant type="int" value="60000"/>
       </cast>
      </binaryOp>
      <binaryOp name="gte">
       <field name="corporateScore">
        <variable name="report"/>
       </field>
       <constant type="int" value="900"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="grade">
      <variable name="report"/>
     </field>
     <constant type="string" value="C"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="report"/>
     <arguments>
      <constant type="string" value="Average risk loan"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="grade_10">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;eligibility&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="gte">
       <field name="yearlyRepayment">
        <variable name="loan"/>
       </field>
       <cast type="double">
        <constant type="int" value="60000"/>
       </cast>
      </binaryOp>
      <binaryOp name="in">
       <field name="corporateScore">
        <variable name="report"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="600"/>
        <constant type="int" value="900"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="grade">
      <variable name="report"/>
     </field>
     <constant type="string" value="D"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="report"/>
     <arguments>
      <constant type="string" value="Risky loan"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="grade_11">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;eligibility.grade&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;eligibility&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="gte">
       <field name="yearlyRepayment">
        <variable name="loan"/>
       </field>
       <cast type="double">
        <constant type="int" value="60000"/>
       </cast>
      </binaryOp>
      <binaryOp name="in">
       <field name="corporateScore">
        <variable name="report"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="300"/>
        <constant type="int" value="600"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="grade">
      <variable name="report"/>
     </field>
     <constant type="string" value="E"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="report"/>
     <arguments>
      <constant type="string" value="Very risky loan"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="approval">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;eligibility&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;eligibility.approval&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <field name="approved">
       <variable name="report"/>
      </field>
      <binaryOp name="in">
       <field name="grade">
        <variable name="report"/>
       </field>
       <newArray className="java.lang.Object">
        <realArray value="false"/>
        <dimension value="1"/>
        <initValues>
         <constant type="string" value="A"/>
         <constant type="string" value="B"/>
         <constant type="string" value="C"/>
        </initValues>
       </newArray>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="translation.loan.Report.approveLoan">
     <arguments>
      <variable name="report"/>
      <constant type="string" value="Congratulations! Your loan has been approved"/>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
   <else>
    <method name="translation.loan.Report.rejectLoan">
     <arguments>
      <variable name="report"/>
      <constant type="string" value="We are sorry. Your loan has not been approved"/>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </else>
  </rule>
  <rule name="checkIncome">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;eligibility&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;eligibility.checkIncome&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <bind name="minimum_income">
      <binaryOp name="*">
       <constant type="double" value="0.37"/>
       <cast type="double">
        <field name="yearlyIncome">
         <variable name="borrower"/>
        </field>
       </cast>
      </binaryOp>
     </bind>
    </evaluateCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="gte">
      <field name="yearlyRepayment">
       <variable name="loan"/>
      </field>
      <variable name="minimum_income"/>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="translation.loan.Report.rejectLoan">
     <arguments>
      <variable name="report"/>
      <binaryOp name="+">
       <constant type="string" value="Too big Debt&#47;Income ratio: "/>
       <method name="formattedAmount">
        <class className="loan.LoanUtil"/>
        <arguments>
         <binaryOp name="/">
          <field name="yearlyRepayment">
           <variable name="loan"/>
          </field>
          <cast type="double">
           <field name="yearlyIncome">
            <variable name="borrower"/>
           </field>
          </cast>
         </binaryOp>
        </arguments>
       </method>
      </binaryOp>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="checkCreditScore">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;eligibility&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;eligibility.checkCreditScore&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="ilog.rules.engine.IlrContext">
     <enumerator name="from">
      <variable name="context"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <bind name="minimum_score">
      <constant type="int" value="200"/>
     </bind>
    </evaluateCondition>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="lt">
      <field name="creditScore">
       <variable name="borrower"/>
      </field>
      <variable name="minimum_score"/>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="translation.loan.Report.rejectLoan">
     <arguments>
      <variable name="report"/>
      <binaryOp name="+">
       <constant type="string" value="Credit score below "/>
       <method name="translation.loan.LoanUtil.formattedNumber">
        <arguments>
         <variable name="minimum_score"/>
        </arguments>
       </method>
      </binaryOp>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
 </package>
</ruleset>
