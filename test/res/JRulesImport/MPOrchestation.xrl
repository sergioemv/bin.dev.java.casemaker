<?xml version="1.0" encoding="ISO-8859-1"?>
<ruleset name="loanvalidation$45$orchestration" className="ilog.rules.engine.IlrContext" xmlns="http://www.ilog.com/products/xml/schemas/xrl_2.0">
 <properties>
  <property name="orchestration">
   <constant type="string" value="&quot;orchestration_&quot;"/>
  </property>
  <property name="computation">
   <constant type="string" value="&quot;computation_&quot;"/>
  </property>
  <property name="validation">
   <constant type="string" value="&quot;validation_&quot;"/>
  </property>
  <property name="insurance">
   <constant type="string" value="&quot;insurance_&quot;"/>
  </property>
  <property name="eligibility">
   <constant type="string" value="&quot;eligibility_&quot;"/>
  </property>
 </properties>
 <rulesetParameters>
  <argument modifier="3" type="loan.Borrower" name="orchestration_borrower">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="3" type="financial.AggregateLoan" name="orchestration_loans">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="7" type="loan.Loan" name="orchestration_loan">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="3" type="loan.Borrower" name="computation_borrower">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="7" type="loan.Loan" name="computation_loan">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="3" type="loan.Borrower" name="eligibility_borrower">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="3" type="financial.AggregateLoan" name="eligibility_loans">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="7" type="loan.Loan" name="eligibility_loan">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="3" type="loan.Borrower" name="insurance_borrower">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="7" type="loan.Loan" name="insurance_loan">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="3" type="loan.Borrower" name="validation_borrower">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="7" type="loan.Loan" name="validation_loan">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="5" type="loan.Report" name="orchestration_report">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="5" type="loan.Report" name="computation_report">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="5" type="loan.Report" name="eligibility_report">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="5" type="loan.Report" name="insurance_report">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="5" type="loan.Report" name="validation_report">
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
  <function name="ilrmain" type="void">
   <argument name="arg" type="java.lang.Object"/>
   <block>
    <assign name="=">
     <variable name="orchestration_borrower"/>
     <new className="loan.Borrower">
      <arguments>
       <constant type="string" value="John"/>
       <constant type="string" value="Doe"/>
       <method name="makeDate">
        <class className="loan.DateUtil"/>
        <arguments>
         <constant type="int" value="1968"/>
         <field name="MAY">
          <class className="java.util.Calendar"/>
         </field>
         <constant type="int" value="12"/>
        </arguments>
       </method>
       <constant type="string" value="123456789"/>
      </arguments>
     </new>
    </assign>
    <assign name="=">
     <field name="zipCode">
      <variable name="orchestration_borrower"/>
     </field>
     <constant type="string" value="91320"/>
    </assign>
    <assign name="=">
     <field name="creditScore">
      <variable name="orchestration_borrower"/>
     </field>
     <constant type="int" value="600"/>
    </assign>
    <assign name="=">
     <field name="yearlyIncome">
      <variable name="orchestration_borrower"/>
     </field>
     <constant type="int" value="100000"/>
    </assign>
    <assign name="=">
     <variable name="orchestration_loan"/>
     <new className="loan.Loan">
      <arguments>
       <method name="makeDate">
        <class className="loan.DateUtil"/>
        <arguments>
         <constant type="int" value="2005"/>
         <field name="JUNE">
          <class className="java.util.Calendar"/>
         </field>
         <constant type="int" value="1"/>
        </arguments>
       </method>
       <constant type="int" value="72"/>
       <constant type="int" value="100000"/>
       <constant type="double" value="0.7"/>
      </arguments>
     </new>
    </assign>
    <var name="reader" type="java.io.Reader">
     <cast type="java.io.Reader">
      <new className="java.io.StringReader">
       <arguments>
        <constant type="string" value="&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;&lt;AggregateLoan xmlns:x0=&quot;http:&#47;&#47;www.w3.org&#47;2001&#47;XMLSchema&quot;&gt;&lt;ssn&gt;&lt;area&gt;123&lt;&#47;area&gt;&lt;group&gt;45&lt;&#47;group&gt;&lt;serial&gt;6789&lt;&#47;serial&gt;&lt;&#47;ssn&gt;&lt;amount&gt;0&lt;&#47;amount&gt;&lt;&#47;AggregateLoan&gt;"/>
       </arguments>
      </new>
     </cast>
    </var>
    <assign name="=">
     <variable name="orchestration_loans"/>
     <cast type="financial.AggregateLoan">
      <method name="getIlrXmlObject">
       <class className="utilities.AggregateLoanReader"/>
       <arguments>
        <field name="reflect">
         <field name="ruleset">
          <variable name="context"/>
         </field>
        </field>
        <variable name="reader"/>
       </arguments>
      </method>
     </cast>
    </assign>
    <method name="execute">
     <variable name="context"/>
    </method>
   </block>
  </function>
  <flowtask name="orchestration">
   <properties>
    <property name="mainflowtask">
     <constant type="string" value="true"/>
    </property>
   </properties>
   <initialActions>
    <block>
    </block>
   </initialActions>
   <finalActions>
    <block>
    </block>
   </finalActions>
   <body>
    <taskInstance taskname="orchestration#initialization" name="null"/>
    <taskInstance taskname="orchestration#validation" name="null"/>
    <if name="null">
     <field name="validData">
      <variable name="orchestration_report"/>
     </field>
     <branch>
      <taskInstance taskname="eligibility.evaluation" name="null"/>
      <if name="null">
       <field name="approved">
        <variable name="orchestration_report"/>
       </field>
       <branch>
        <taskInstance taskname="orchestration#insurance" name="null"/>
       </branch>
      </if>
     </branch>
    </if>
   </body>
  </flowtask>
  <ruletask name="orchestration#insurance">
   <initialActions>
    <block>
     <method name="println">
      <field name="out">
       <class className="java.lang.System"/>
      </field>
      <arguments>
       <constant type="string" value="Calling INSURANCE"/>
      </arguments>
     </method>
    </block>
   </initialActions>
   <rulesFiring firing="1" ordering="0" firingLimit="0" algorithm="0"/>
   <dynamicBody value="false"/>
   <body>
    <rule name="insurance.defaultInsurance"/>
    <group groupName="insurance.insurance"/>
   </body>
  </ruletask>
  <functiontask name="orchestration#initialization">
   <body>
    <block>
     <assign name="=">
      <variable name="orchestration_report"/>
      <new className="loan.Report">
       <arguments>
        <variable name="orchestration_borrower"/>
        <variable name="orchestration_loan"/>
       </arguments>
      </new>
     </assign>
     <assign name="=">
      <field name="approved">
       <variable name="orchestration_report"/>
      </field>
      <constant type="boolean" value="true"/>
     </assign>
     <method name="assign">
      <class className="utilities.ParametersHook"/>
      <arguments>
       <variable name="context"/>
       <constant type="string" value="insurance"/>
       <constant type="string" value="orchestration"/>
      </arguments>
     </method>
     <method name="assign">
      <class className="utilities.ParametersHook"/>
      <arguments>
       <variable name="context"/>
       <constant type="string" value="validation"/>
       <constant type="string" value="orchestration"/>
      </arguments>
     </method>
     <method name="assign">
      <class className="utilities.ParametersHook"/>
      <arguments>
       <variable name="context"/>
       <constant type="string" value="computation"/>
       <constant type="string" value="orchestration"/>
      </arguments>
     </method>
     <method name="assign">
      <class className="utilities.ParametersHook"/>
      <arguments>
       <variable name="context"/>
       <constant type="string" value="eligibility"/>
       <constant type="string" value="orchestration"/>
      </arguments>
     </method>
    </block>
   </body>
  </functiontask>
  <ruletask name="orchestration#validation">
   <properties>
    <property name="ilog.rules.engine.sequential.fastpath">
     <constant type="string" value="true"/>
    </property>
   </properties>
   <initialActions>
    <block>
     <method name="println">
      <field name="out">
       <class className="java.lang.System"/>
      </field>
      <arguments>
       <constant type="string" value="Calling VALIDATION"/>
      </arguments>
     </method>
    </block>
   </initialActions>
   <finalActions>
    <block>
    </block>
   </finalActions>
   <rulesFiring firing="1" ordering="1" firingLimit="0" algorithm="1"/>
   <dynamicBody value="false"/>
   <body>
    <package pkgName="validation"/>
    <package pkgName="validation.borrower"/>
    <package pkgName="validation.loan"/>
    <package pkgName="validation.test"/>
   </body>
  </ruletask>
 </default-package>
 <package pkgName="computation">
  <packageVariables>
  </packageVariables>
  <rule name="rate_0">
   <properties>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="lt">
       <field name="duration">
        <variable name="computation_loan"/>
       </field>
       <constant type="int" value="5"/>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="computation_loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="0"/>
        <constant type="double" value="0.7"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="lt">
       <field name="duration">
        <variable name="computation_loan"/>
       </field>
       <constant type="int" value="5"/>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="lt">
       <field name="duration">
        <variable name="computation_loan"/>
       </field>
       <constant type="int" value="5"/>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="lt">
       <field name="duration">
        <variable name="computation_loan"/>
       </field>
       <constant type="int" value="5"/>
      </binaryOp>
      <binaryOp name="gte">
       <field name="loanToValue">
        <variable name="computation_loan"/>
       </field>
       <constant type="double" value="0.9"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="computation_loan"/>
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
        <variable name="computation_loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="0"/>
        <constant type="double" value="0.7"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="computation_loan"/>
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
        <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="computation_loan"/>
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
        <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="computation_loan"/>
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
        <variable name="computation_loan"/>
       </field>
       <constant type="double" value="0.9"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="computation_loan"/>
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
        <variable name="computation_loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="0"/>
        <constant type="double" value="0.7"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="computation_loan"/>
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
        <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="computation_loan"/>
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
        <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="computation_loan"/>
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
        <variable name="computation_loan"/>
       </field>
       <constant type="double" value="0.9"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="computation_loan"/>
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
        <variable name="computation_loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="0"/>
        <constant type="double" value="0.7"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="computation_loan"/>
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
        <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="computation_loan"/>
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
        <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="computation_loan"/>
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
        <variable name="computation_loan"/>
       </field>
       <constant type="double" value="0.9"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="computation_loan"/>
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
        <variable name="computation_loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="0"/>
        <constant type="double" value="0.7"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="computation_loan"/>
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
        <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="computation_loan"/>
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
        <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="duration">
        <variable name="computation_loan"/>
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
        <variable name="computation_loan"/>
       </field>
       <constant type="double" value="0.9"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="gte">
       <field name="duration">
        <variable name="computation_loan"/>
       </field>
       <constant type="int" value="26"/>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="computation_loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="0"/>
        <constant type="double" value="0.7"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="gte">
       <field name="duration">
        <variable name="computation_loan"/>
       </field>
       <constant type="int" value="26"/>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="gte">
       <field name="duration">
        <variable name="computation_loan"/>
       </field>
       <constant type="int" value="26"/>
      </binaryOp>
      <binaryOp name="in">
       <field name="loanToValue">
        <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="gte">
       <field name="duration">
        <variable name="computation_loan"/>
       </field>
       <constant type="int" value="26"/>
      </binaryOp>
      <binaryOp name="gte">
       <field name="loanToValue">
        <variable name="computation_loan"/>
       </field>
       <constant type="double" value="0.9"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="yearlyInterestRate">
      <variable name="computation_loan"/>
     </field>
     <constant type="double" value="0.085"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
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
      <variable name="computation_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <unaryOp name="!">
      <method name="hasLatestBankrupcy">
       <variable name="computation_borrower"/>
      </method>
     </unaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addCorporateScore">
     <variable name="computation_report"/>
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
      <variable name="computation_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="lt">
      <field name="yearlyIncome">
       <variable name="computation_borrower"/>
      </field>
      <constant type="int" value="10000"/>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addCorporateScore">
     <variable name="computation_report"/>
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
      <variable name="computation_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="in">
      <field name="yearlyIncome">
       <variable name="computation_borrower"/>
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
     <variable name="computation_report"/>
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
      <variable name="computation_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="in">
      <field name="yearlyIncome">
       <variable name="computation_borrower"/>
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
     <variable name="computation_report"/>
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
      <variable name="computation_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="in">
      <field name="yearlyIncome">
       <variable name="computation_borrower"/>
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
     <variable name="computation_report"/>
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
      <variable name="computation_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="in">
      <field name="yearlyIncome">
       <variable name="computation_borrower"/>
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
     <variable name="computation_report"/>
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
      <variable name="computation_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="in">
      <field name="yearlyIncome">
       <variable name="computation_borrower"/>
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
     <variable name="computation_report"/>
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
      <variable name="computation_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="in">
      <field name="yearlyIncome">
       <variable name="computation_borrower"/>
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
     <variable name="computation_report"/>
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
      <variable name="computation_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="gte">
      <field name="yearlyIncome">
       <variable name="computation_borrower"/>
      </field>
      <constant type="int" value="200000"/>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addCorporateScore">
     <variable name="computation_report"/>
     <arguments>
      <constant type="int" value="300"/>
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
      <variable name="computation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <bind name="amount">
      <field name="amount">
       <variable name="computation_loan"/>
      </field>
     </bind>
    </evaluateCondition>
    <evaluateCondition>
     <bind name="duration">
      <field name="numberOfMonthlyPayments">
       <variable name="computation_loan"/>
      </field>
     </bind>
    </evaluateCondition>
    <evaluateCondition>
     <bind name="rate">
      <field name="yearlyInterestRate">
       <variable name="computation_loan"/>
      </field>
     </bind>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="monthlyRepayment">
      <variable name="computation_loan"/>
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
  <rule name="initialCorporateScore">
   <priority>
    <field name="high">
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
      <variable name="computation_report"/>
     </field>
     <field name="creditScore">
      <variable name="computation_borrower"/>
     </field>
    </assign>
   </then>
  </rule>
  <rule name="bankruptcyScore_0">
   <properties>
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
      <variable name="computation_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <method name="hasLatestBankrupcy">
       <variable name="computation_borrower"/>
      </method>
      <binaryOp name="in">
       <method name="translation.loan.Borrower.getBankruptcyAge">
        <arguments>
         <variable name="computation_borrower"/>
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
     <variable name="computation_report"/>
     <arguments>
      <constant type="int" value="-200"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="bankruptcyScore_1">
   <properties>
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
      <variable name="computation_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <method name="hasLatestBankrupcy">
       <variable name="computation_borrower"/>
      </method>
      <binaryOp name="in">
       <method name="translation.loan.Borrower.getBankruptcyAge">
        <arguments>
         <variable name="computation_borrower"/>
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
     <variable name="computation_report"/>
     <arguments>
      <constant type="int" value="-100"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="bankruptcyScore_2">
   <properties>
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
      <variable name="computation_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <method name="hasLatestBankrupcy">
       <variable name="computation_borrower"/>
      </method>
      <binaryOp name="in">
       <method name="translation.loan.Borrower.getBankruptcyAge">
        <arguments>
         <variable name="computation_borrower"/>
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
     <variable name="computation_report"/>
     <arguments>
      <constant type="int" value="-50"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="bankruptcyScore_3">
   <properties>
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
      <variable name="computation_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <method name="hasLatestBankrupcy">
       <variable name="computation_borrower"/>
      </method>
      <unaryOp name="!">
       <naryOp name="or">
        <binaryOp name="in">
         <method name="translation.loan.Borrower.getBankruptcyAge">
          <arguments>
           <variable name="computation_borrower"/>
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
           <variable name="computation_borrower"/>
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
           <variable name="computation_borrower"/>
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
     <variable name="computation_report"/>
     <arguments>
      <constant type="int" value="-10"/>
     </arguments>
    </method>
   </then>
  </rule>
 </package>
 <package pkgName="validation">
  <packageVariables>
  </packageVariables>
 </package>
 <package pkgName="translation.loan.Report">
  <packageVariables>
  </packageVariables>
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
 </package>
 <package pkgName="validation.borrower">
  <packageVariables>
  </packageVariables>
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
      <variable name="validation_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="in">
      <field name="areaNumber">
       <method name="translation.loan.Borrower.ssn">
        <arguments>
         <variable name="validation_borrower"/>
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
      <variable name="validation_report"/>
      <binaryOp name="+">
       <constant type="string" value="The borrower&apos;s SSN area number cannot be "/>
       <field name="areaNumber">
        <method name="translation.loan.Borrower.ssn">
         <arguments>
          <variable name="validation_borrower"/>
         </arguments>
        </method>
       </field>
      </binaryOp>
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
      <variable name="validation_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="!=">
      <method name="length">
       <field name="zipCode">
        <variable name="validation_borrower"/>
       </field>
      </method>
      <variable name="zip_length"/>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="translation.loan.Report.rejectData">
     <arguments>
      <variable name="validation_report"/>
      <binaryOp name="+">
       <binaryOp name="+">
        <constant type="string" value="The borrower&apos;s Zip Code should have "/>
        <variable name="zip_length"/>
       </binaryOp>
       <constant type="string" value=" digits"/>
      </binaryOp>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="checkName">
   <comment>
 </comment>
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
      <variable name="validation_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="or">
      <binaryOp name="==">
       <field name="lastName">
        <variable name="validation_borrower"/>
       </field>
       <constant type="null" value="null"/>
      </binaryOp>
      <binaryOp name="==">
       <method name="length">
        <field name="lastName">
         <variable name="validation_borrower"/>
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
      <variable name="validation_report"/>
      <constant type="string" value="The borrower&apos;s name is empty"/>
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
      <variable name="validation_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="or">
      <binaryOp name="!=">
       <field name="digits">
        <method name="translation.loan.Borrower.ssn">
         <arguments>
          <variable name="validation_borrower"/>
         </arguments>
        </method>
       </field>
       <variable name="digits"/>
      </binaryOp>
      <binaryOp name="!=">
       <method name="length">
        <field name="areaNumber">
         <method name="translation.loan.Borrower.ssn">
          <arguments>
           <variable name="validation_borrower"/>
          </arguments>
         </method>
        </field>
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
            <variable name="validation_borrower"/>
           </arguments>
          </method>
         </field>
        </arguments>
       </method>
      </unaryOp>
      <binaryOp name="!=">
       <method name="length">
        <field name="groupCode">
         <method name="translation.loan.Borrower.ssn">
          <arguments>
           <variable name="validation_borrower"/>
          </arguments>
         </method>
        </field>
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
            <variable name="validation_borrower"/>
           </arguments>
          </method>
         </field>
        </arguments>
       </method>
      </unaryOp>
      <binaryOp name="!=">
       <method name="length">
        <field name="serialNumber">
         <method name="translation.loan.Borrower.ssn">
          <arguments>
           <variable name="validation_borrower"/>
          </arguments>
         </method>
        </field>
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
            <variable name="validation_borrower"/>
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
      <variable name="validation_report"/>
      <constant type="string" value="The borrower&apos;s SSN should be formatted with 3-2-4 digits"/>
     </arguments>
    </method>
   </then>
  </rule>
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
      <constant type="int" value="100"/>
     </bind>
    </evaluateCondition>
    <simpleCondition asEvent="no" className="loan.Borrower">
     <enumerator name="from">
      <variable name="validation_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <unaryOp name="!">
      <binaryOp name="in">
       <method name="translation.loan.Borrower.age">
        <arguments>
         <variable name="validation_borrower"/>
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
      <variable name="validation_report"/>
      <constant type="string" value="The borrower&apos;s age is not valid."/>
     </arguments>
    </method>
   </then>
  </rule>
 </package>
 <package pkgName="computation.test">
  <packageVariables>
  </packageVariables>
  <flowtask name="evaluation">
   <properties>
    <property name="mainflowtask">
     <constant type="string" value="false"/>
    </property>
   </properties>
   <finalActions>
    <block>
     <method name="println">
      <field name="out">
       <variable name="context"/>
      </field>
      <arguments>
       <variable name="computation_borrower"/>
      </arguments>
     </method>
     <method name="println">
      <field name="out">
       <variable name="context"/>
      </field>
      <arguments>
       <variable name="computation_loan"/>
      </arguments>
     </method>
     <method name="println">
      <field name="out">
       <variable name="context"/>
      </field>
      <arguments>
       <variable name="computation_report"/>
      </arguments>
     </method>
    </block>
   </finalActions>
   <body>
    <taskInstance taskname="computation.test.evaluation#initResult" name="null"/>
    <taskInstance taskname="computation.test.evaluation#computation" name="null"/>
   </body>
  </flowtask>
  <ruletask name="evaluation#computation">
   <initialActions>
    <block>
    </block>
   </initialActions>
   <rulesFiring firing="1" ordering="0" firingLimit="0" algorithm="0"/>
   <dynamicBody value="false"/>
   <body>
    <package pkgName="computation"/>
    <package pkgName="computation.test"/>
   </body>
  </ruletask>
  <functiontask name="evaluation#initResult">
   <body>
    <block>
     <assign name="=">
      <variable name="computation_report"/>
      <new className="loan.Report">
       <arguments>
        <variable name="computation_borrower"/>
        <variable name="computation_loan"/>
       </arguments>
      </new>
     </assign>
     <assign name="=">
      <field name="approved">
       <variable name="computation_report"/>
      </field>
      <constant type="boolean" value="true"/>
     </assign>
    </block>
   </body>
  </functiontask>
 </package>
 <package pkgName="validation.test">
  <packageVariables>
  </packageVariables>
  <flowtask name="evaluation">
   <properties>
    <property name="mainflowtask">
     <constant type="string" value="false"/>
    </property>
   </properties>
   <finalActions>
    <block>
     <method name="println">
      <field name="out">
       <variable name="context"/>
      </field>
      <arguments>
       <variable name="validation_borrower"/>
      </arguments>
     </method>
     <method name="println">
      <field name="out">
       <variable name="context"/>
      </field>
      <arguments>
       <variable name="validation_loan"/>
      </arguments>
     </method>
     <method name="println">
      <field name="out">
       <variable name="context"/>
      </field>
      <arguments>
       <variable name="validation_report"/>
      </arguments>
     </method>
    </block>
   </finalActions>
   <body>
    <taskInstance taskname="validation.test.evaluation#validation" name="null"/>
   </body>
  </flowtask>
  <ruletask name="evaluation#validation">
   <initialActions>
    <block>
     <assign name="=">
      <variable name="validation_report"/>
      <new className="loan.Report">
       <arguments>
        <variable name="validation_borrower"/>
        <variable name="validation_loan"/>
       </arguments>
      </new>
     </assign>
     <assign name="=">
      <field name="approved">
       <variable name="validation_report"/>
      </field>
      <constant type="boolean" value="true"/>
     </assign>
    </block>
   </initialActions>
   <finalActions>
    <block>
    </block>
   </finalActions>
   <rulesFiring firing="1" ordering="0" firingLimit="0" algorithm="0"/>
   <dynamicBody value="false"/>
   <body>
    <package pkgName="validation.borrower"/>
    <package pkgName="validation.loan"/>
   </body>
  </ruletask>
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
      <variable name="validation_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="gte">
      <field name="amount">
       <variable name="validation_loan"/>
      </field>
      <variable name="maxAmount"/>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="translation.loan.Report.rejectData">
     <arguments>
      <variable name="validation_report"/>
      <binaryOp name="+">
       <constant type="string" value="The loan cannot exceed "/>
       <variable name="maxAmount"/>
      </binaryOp>
     </arguments>
    </method>
   </then>
  </rule>
 </package>
 <package pkgName="eligibility.test">
  <packageVariables>
  </packageVariables>
  <flowtask name="evaluation">
   <properties>
    <property name="mainflowtask">
     <constant type="string" value="false"/>
    </property>
   </properties>
   <finalActions>
    <block>
     <method name="println">
      <field name="out">
       <variable name="context"/>
      </field>
      <arguments>
       <variable name="computation_borrower"/>
      </arguments>
     </method>
     <method name="println">
      <field name="out">
       <variable name="context"/>
      </field>
      <arguments>
       <variable name="computation_loan"/>
      </arguments>
     </method>
     <method name="println">
      <field name="out">
       <variable name="context"/>
      </field>
      <arguments>
       <variable name="computation_report"/>
      </arguments>
     </method>
    </block>
   </finalActions>
   <body>
    <taskInstance taskname="eligibility.test.evaluation#initialization" name="null"/>
    <taskInstance taskname="eligibility.evaluation" name="null"/>
   </body>
  </flowtask>
  <functiontask name="evaluation#initialization">
   <body>
    <block>
     <assign name="=">
      <variable name="eligibility_report"/>
      <new className="loan.Report">
       <arguments>
        <variable name="eligibility_borrower"/>
        <variable name="eligibility_loan"/>
       </arguments>
      </new>
     </assign>
     <assign name="=">
      <field name="approved">
       <variable name="eligibility_report"/>
      </field>
      <constant type="boolean" value="true"/>
     </assign>
     <method name="assign">
      <class className="utilities.ParametersHook"/>
      <arguments>
       <variable name="context"/>
       <constant type="string" value="computation"/>
       <constant type="string" value="eligibility"/>
      </arguments>
     </method>
    </block>
   </body>
  </functiontask>
 </package>
 <package pkgName="insurance.test">
  <packageVariables>
  </packageVariables>
  <flowtask name="evaluation">
   <properties>
    <property name="mainflowtask">
     <constant type="string" value="false"/>
    </property>
   </properties>
   <finalActions>
    <block>
     <method name="println">
      <field name="out">
       <variable name="context"/>
      </field>
      <arguments>
       <variable name="insurance_borrower"/>
      </arguments>
     </method>
     <method name="println">
      <field name="out">
       <variable name="context"/>
      </field>
      <arguments>
       <variable name="insurance_loan"/>
      </arguments>
     </method>
     <method name="println">
      <field name="out">
       <variable name="context"/>
      </field>
      <arguments>
       <variable name="insurance_report"/>
      </arguments>
     </method>
    </block>
   </finalActions>
   <body>
    <taskInstance taskname="insurance.test.evaluation#insurance" name="null"/>
   </body>
  </flowtask>
  <ruletask name="evaluation#insurance">
   <initialActions>
    <block>
     <assign name="=">
      <variable name="insurance_report"/>
      <new className="loan.Report">
       <arguments>
        <variable name="insurance_borrower"/>
        <variable name="insurance_loan"/>
       </arguments>
      </new>
     </assign>
     <assign name="=">
      <field name="approved">
       <variable name="insurance_report"/>
      </field>
      <constant type="boolean" value="true"/>
     </assign>
    </block>
   </initialActions>
   <finalActions>
    <block>
    </block>
   </finalActions>
   <rulesFiring firing="1" ordering="0" firingLimit="0" algorithm="0"/>
   <dynamicBody value="false"/>
   <body>
    <rule name="insurance.defaultInsurance"/>
    <group groupName="insurance.insurance"/>
   </body>
  </ruletask>
 </package>
 <package pkgName="insurance">
  <packageVariables>
  </packageVariables>
  <rule name="insurance_0">
   <properties>
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
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="A"/>
        </arguments>
       </method>
       <binaryOp name="lt">
        <field name="amount">
         <variable name="insurance_loan"/>
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
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="false"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_1">
   <properties>
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
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="A"/>
        </arguments>
       </method>
       <binaryOp name="in">
        <field name="amount">
         <variable name="insurance_loan"/>
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
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0010"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_2">
   <properties>
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
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="A"/>
        </arguments>
       </method>
       <binaryOp name="in">
        <field name="amount">
         <variable name="insurance_loan"/>
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
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0030"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_3">
   <properties>
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
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="A"/>
        </arguments>
       </method>
       <binaryOp name="gte">
        <field name="amount">
         <variable name="insurance_loan"/>
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
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0050"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_4">
   <properties>
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
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="B"/>
        </arguments>
       </method>
       <binaryOp name="lt">
        <field name="amount">
         <variable name="insurance_loan"/>
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
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="false"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_5">
   <properties>
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
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="B"/>
        </arguments>
       </method>
       <binaryOp name="in">
        <field name="amount">
         <variable name="insurance_loan"/>
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
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0025"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_6">
   <properties>
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
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="B"/>
        </arguments>
       </method>
       <binaryOp name="in">
        <field name="amount">
         <variable name="insurance_loan"/>
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
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0050"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_7">
   <properties>
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
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="B"/>
        </arguments>
       </method>
       <binaryOp name="gte">
        <field name="amount">
         <variable name="insurance_loan"/>
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
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0075"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_8">
   <properties>
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
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="C"/>
        </arguments>
       </method>
       <binaryOp name="lt">
        <field name="amount">
         <variable name="insurance_loan"/>
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
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0035"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_9">
   <properties>
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
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="C"/>
        </arguments>
       </method>
       <binaryOp name="in">
        <field name="amount">
         <variable name="insurance_loan"/>
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
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0060"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_10">
   <properties>
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
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="C"/>
        </arguments>
       </method>
       <binaryOp name="in">
        <field name="amount">
         <variable name="insurance_loan"/>
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
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0085"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_11">
   <properties>
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
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="C"/>
        </arguments>
       </method>
       <binaryOp name="gte">
        <field name="amount">
         <variable name="insurance_loan"/>
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
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0145"/>
    </assign>
   </then>
  </rule>
  <rule name="defaultInsurance">
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
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.02"/>
    </assign>
   </then>
  </rule>
 </package>
 <package pkgName="eligibility">
  <packageVariables>
  </packageVariables>
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
      <variable name="eligibility_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <bind name="minimum_income">
      <binaryOp name="*">
       <constant type="double" value="0.37"/>
       <field name="yearlyIncome">
        <variable name="eligibility_borrower"/>
       </field>
      </binaryOp>
     </bind>
    </evaluateCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="eligibility_loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="financial.AggregateLoan">
     <enumerator name="from">
      <variable name="eligibility_loans"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="gte">
      <binaryOp name="+">
       <field name="yearlyRepayment">
        <variable name="eligibility_loan"/>
       </field>
       <field name="amount">
        <variable name="eligibility_loans"/>
       </field>
      </binaryOp>
      <variable name="minimum_income"/>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="translation.loan.Report.rejectLoan">
     <arguments>
      <variable name="eligibility_report"/>
      <binaryOp name="+">
       <constant type="string" value="Too big Debt&#47;Income ratio: "/>
       <method name="formattedAmount">
        <class className="loan.LoanUtil"/>
        <arguments>
         <binaryOp name="/">
          <field name="yearlyRepayment">
           <variable name="eligibility_loan"/>
          </field>
          <field name="yearlyIncome">
           <variable name="eligibility_borrower"/>
          </field>
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
  <rule name="approval">
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
     <constant type="string" value="&quot;eligibility&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;eligibility.approval&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="eligibility_report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <field name="approved">
       <variable name="eligibility_report"/>
      </field>
      <binaryOp name="in">
       <field name="grade">
        <variable name="eligibility_report"/>
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
      <variable name="eligibility_report"/>
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
      <variable name="eligibility_report"/>
      <constant type="string" value="We are sorry. Your loan has not been approved"/>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </else>
  </rule>
  <rule name="grade_0">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
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
      <variable name="eligibility_loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="eligibility_report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="yearlyRepayment">
        <variable name="eligibility_loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="0"/>
        <constant type="int" value="10000"/>
       </interval>
      </binaryOp>
      <binaryOp name="gte">
       <field name="corporateScore">
        <variable name="eligibility_report"/>
       </field>
       <constant type="int" value="900"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="grade">
      <variable name="eligibility_report"/>
     </field>
     <constant type="string" value="A"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="eligibility_report"/>
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
      <variable name="eligibility_loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="eligibility_report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="yearlyRepayment">
        <variable name="eligibility_loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="0"/>
        <constant type="int" value="10000"/>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="corporateScore">
        <variable name="eligibility_report"/>
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
      <variable name="eligibility_report"/>
     </field>
     <constant type="string" value="A"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="eligibility_report"/>
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
      <variable name="eligibility_loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="eligibility_report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="yearlyRepayment">
        <variable name="eligibility_loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="0"/>
        <constant type="int" value="10000"/>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="corporateScore">
        <variable name="eligibility_report"/>
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
      <variable name="eligibility_report"/>
     </field>
     <constant type="string" value="B"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="eligibility_report"/>
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
      <variable name="eligibility_loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="eligibility_report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="yearlyRepayment">
        <variable name="eligibility_loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="10000"/>
        <constant type="int" value="30000"/>
       </interval>
      </binaryOp>
      <binaryOp name="gte">
       <field name="corporateScore">
        <variable name="eligibility_report"/>
       </field>
       <constant type="int" value="900"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="grade">
      <variable name="eligibility_report"/>
     </field>
     <constant type="string" value="A"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="eligibility_report"/>
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
      <variable name="eligibility_loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="eligibility_report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="yearlyRepayment">
        <variable name="eligibility_loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="10000"/>
        <constant type="int" value="30000"/>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="corporateScore">
        <variable name="eligibility_report"/>
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
      <variable name="eligibility_report"/>
     </field>
     <constant type="string" value="B"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="eligibility_report"/>
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
      <variable name="eligibility_loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="eligibility_report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="yearlyRepayment">
        <variable name="eligibility_loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="10000"/>
        <constant type="int" value="30000"/>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="corporateScore">
        <variable name="eligibility_report"/>
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
      <variable name="eligibility_report"/>
     </field>
     <constant type="string" value="C"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="eligibility_report"/>
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
      <variable name="eligibility_loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="eligibility_report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="yearlyRepayment">
        <variable name="eligibility_loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="30000"/>
        <constant type="int" value="60000"/>
       </interval>
      </binaryOp>
      <binaryOp name="gte">
       <field name="corporateScore">
        <variable name="eligibility_report"/>
       </field>
       <constant type="int" value="900"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="grade">
      <variable name="eligibility_report"/>
     </field>
     <constant type="string" value="B"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="eligibility_report"/>
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
      <variable name="eligibility_loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="eligibility_report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="yearlyRepayment">
        <variable name="eligibility_loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="30000"/>
        <constant type="int" value="60000"/>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="corporateScore">
        <variable name="eligibility_report"/>
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
      <variable name="eligibility_report"/>
     </field>
     <constant type="string" value="C"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="eligibility_report"/>
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
      <variable name="eligibility_loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="eligibility_report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="in">
       <field name="yearlyRepayment">
        <variable name="eligibility_loan"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="true"/>
        <constant type="int" value="30000"/>
        <constant type="int" value="60000"/>
       </interval>
      </binaryOp>
      <binaryOp name="in">
       <field name="corporateScore">
        <variable name="eligibility_report"/>
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
      <variable name="eligibility_report"/>
     </field>
     <constant type="string" value="D"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="eligibility_report"/>
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
      <variable name="eligibility_loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="eligibility_report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="gte">
       <field name="yearlyRepayment">
        <variable name="eligibility_loan"/>
       </field>
       <constant type="int" value="60000"/>
      </binaryOp>
      <binaryOp name="gte">
       <field name="corporateScore">
        <variable name="eligibility_report"/>
       </field>
       <constant type="int" value="900"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="grade">
      <variable name="eligibility_report"/>
     </field>
     <constant type="string" value="C"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="eligibility_report"/>
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
      <variable name="eligibility_loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="eligibility_report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="gte">
       <field name="yearlyRepayment">
        <variable name="eligibility_loan"/>
       </field>
       <constant type="int" value="60000"/>
      </binaryOp>
      <binaryOp name="in">
       <field name="corporateScore">
        <variable name="eligibility_report"/>
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
      <variable name="eligibility_report"/>
     </field>
     <constant type="string" value="D"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="eligibility_report"/>
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
      <variable name="eligibility_loan"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="eligibility_report"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="gte">
       <field name="yearlyRepayment">
        <variable name="eligibility_loan"/>
       </field>
       <constant type="int" value="60000"/>
      </binaryOp>
      <binaryOp name="in">
       <field name="corporateScore">
        <variable name="eligibility_report"/>
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
      <variable name="eligibility_report"/>
     </field>
     <constant type="string" value="E"/>
    </assign>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="addMessage">
     <variable name="eligibility_report"/>
     <arguments>
      <constant type="string" value="Very risky loan"/>
     </arguments>
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
      <variable name="eligibility_borrower"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="lt">
      <field name="creditScore">
       <variable name="eligibility_borrower"/>
      </field>
      <variable name="minimum_score"/>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="translation.loan.Report.rejectLoan">
     <arguments>
      <variable name="eligibility_report"/>
      <binaryOp name="+">
       <constant type="string" value="Credit score below "/>
       <variable name="minimum_score"/>
      </binaryOp>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <flowtask name="evaluation">
   <properties>
    <property name="mainflowtask">
     <constant type="string" value="false"/>
    </property>
   </properties>
   <initialActions>
    <block>
     <method name="println">
      <field name="out">
       <class className="java.lang.System"/>
      </field>
      <arguments>
       <constant type="string" value="Calling COMPUTATION and ELIGIBILITY"/>
      </arguments>
     </method>
    </block>
   </initialActions>
   <body>
    <taskInstance taskname="eligibility.evaluation#computation" name="null"/>
    <taskInstance taskname="eligibility.evaluation#eligibility" name="null"/>
   </body>
  </flowtask>
  <ruletask name="evaluation#computation">
   <initialActions>
    <block>
    </block>
   </initialActions>
   <rulesFiring firing="1" ordering="0" firingLimit="0" algorithm="0"/>
   <dynamicBody value="false"/>
   <body>
    <group groupName="computation.bankruptcyScore"/>
    <group groupName="computation.initialCorporateScore"/>
    <group groupName="computation.neverBankruptcy"/>
    <group groupName="computation.rate"/>
    <group groupName="computation.repayment"/>
    <group groupName="computation.salary2score"/>
   </body>
  </ruletask>
  <ruletask name="evaluation#eligibility">
   <rulesFiring firing="1" ordering="0" firingLimit="0" algorithm="0"/>
   <dynamicBody value="false"/>
   <body>
    <package pkgName="eligibility"/>
    <package pkgName="eligibility.test"/>
   </body>
  </ruletask>
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
 </package>
</ruleset>
