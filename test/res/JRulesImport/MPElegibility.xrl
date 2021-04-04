<?xml version="1.0" encoding="ISO-8859-1"?>
<ruleset name="loanvalidation$45$eligibility" className="ilog.rules.engine.IlrContext" xmlns="http://www.ilog.com/products/xml/schemas/xrl_2.0">
 <properties>
  <property name="computation">
   <constant type="string" value="&quot;computation_&quot;"/>
  </property>
  <property name="eligibility">
   <constant type="string" value="&quot;eligibility_&quot;"/>
  </property>
 </properties>
 <rulesetParameters>
  <argument modifier="3" type="loan.Borrower" name="eligibility_borrower">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="3" type="financial.AggregateLoan" name="eligibility_loans">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="7" type="loan.Loan" name="eligibility_loan">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="3" type="loan.Borrower" name="computation_borrower">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="7" type="loan.Loan" name="computation_loan">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="5" type="loan.Report" name="eligibility_report">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="5" type="loan.Report" name="computation_report">
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
 </default-package>
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
 </package>
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
</ruleset>
