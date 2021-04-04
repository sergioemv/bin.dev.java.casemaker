<?xml version="1.0" encoding="ISO-8859-1"?>
<ruleset name="loanvalidation$45$computation" className="ilog.rules.engine.IlrContext" xmlns="http://www.ilog.com/products/xml/schemas/xrl_2.0">
 <rulesetParameters>
  <argument modifier="3" type="loan.Borrower" name="computation_borrower">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="7" type="loan.Loan" name="computation_loan">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="5" type="loan.Report" name="computation_report">
   <constant type="null" value="null"/>
  </argument>
 </rulesetParameters>
 <default-package pkgName="">
  <hierarchical-properties>
   <hierarchical-property name="Industry">
    <hierarchical-property-node name="Industry">
     <hierarchical-property-node name="Manufacturing">
      <hierarchical-property-node name="Automotive">
      </hierarchical-property-node>
      <hierarchical-property-node name="Aeronautics and space">
      </hierarchical-property-node>
      <hierarchical-property-node name="Steel">
      </hierarchical-property-node>
     </hierarchical-property-node>
     <hierarchical-property-node name="Technology">
      <hierarchical-property-node name="Telco">
      </hierarchical-property-node>
      <hierarchical-property-node name="Semicon">
      </hierarchical-property-node>
      <hierarchical-property-node name="Software">
      </hierarchical-property-node>
     </hierarchical-property-node>
     <hierarchical-property-node name="Utilities">
      <hierarchical-property-node name="Energy">
       <hierarchical-property-node name="Oil-Gas">
       </hierarchical-property-node>
       <hierarchical-property-node name="Electricity">
       </hierarchical-property-node>
       <hierarchical-property-node name="Nuclear">
       </hierarchical-property-node>
      </hierarchical-property-node>
      <hierarchical-property-node name="Water">
      </hierarchical-property-node>
     </hierarchical-property-node>
     <hierarchical-property-node name="Retail">
     </hierarchical-property-node>
     <hierarchical-property-node name="Transport">
     </hierarchical-property-node>
     <hierarchical-property-node name="Services">
      <hierarchical-property-node name="Banking">
      </hierarchical-property-node>
      <hierarchical-property-node name="Insurance">
      </hierarchical-property-node>
      <hierarchical-property-node name="Financial Services">
      </hierarchical-property-node>
      <hierarchical-property-node name="Travel">
      </hierarchical-property-node>
      <hierarchical-property-node name="Supply Chain Management">
      </hierarchical-property-node>
     </hierarchical-property-node>
     <hierarchical-property-node name="Government">
      <hierarchical-property-node name="Defense">
      </hierarchical-property-node>
      <hierarchical-property-node name="Treasury Commerce">
      </hierarchical-property-node>
      <hierarchical-property-node name="Agriculture">
      </hierarchical-property-node>
     </hierarchical-property-node>
    </hierarchical-property-node>
   </hierarchical-property>
   <hierarchical-property name="Geography">
    <hierarchical-property-node name="World">
     <hierarchical-property-node name="Americas">
      <hierarchical-property-node name="North and Central America">
       <hierarchical-property-node name="Canada">
       </hierarchical-property-node>
       <hierarchical-property-node name="USA">
        <hierarchical-property-node name="Northern California">
        </hierarchical-property-node>
        <hierarchical-property-node name="Southern California">
        </hierarchical-property-node>
        <hierarchical-property-node name="Southwest">
        </hierarchical-property-node>
        <hierarchical-property-node name="Pacific Northwest">
        </hierarchical-property-node>
        <hierarchical-property-node name="Rocky Mountain">
        </hierarchical-property-node>
        <hierarchical-property-node name="North Central">
        </hierarchical-property-node>
        <hierarchical-property-node name="Midwest">
        </hierarchical-property-node>
        <hierarchical-property-node name="SouthCentral">
        </hierarchical-property-node>
        <hierarchical-property-node name="Mid America">
        </hierarchical-property-node>
        <hierarchical-property-node name="Great Lakes">
        </hierarchical-property-node>
        <hierarchical-property-node name="New England">
        </hierarchical-property-node>
        <hierarchical-property-node name="Mid Atlantic">
        </hierarchical-property-node>
        <hierarchical-property-node name="Southeast">
        </hierarchical-property-node>
        <hierarchical-property-node name="Gulf Coast">
        </hierarchical-property-node>
       </hierarchical-property-node>
       <hierarchical-property-node name="Mexico">
       </hierarchical-property-node>
      </hierarchical-property-node>
      <hierarchical-property-node name="South America">
       <hierarchical-property-node name="Argentina">
       </hierarchical-property-node>
       <hierarchical-property-node name="Brazil">
       </hierarchical-property-node>
       <hierarchical-property-node name="Chile">
       </hierarchical-property-node>
      </hierarchical-property-node>
     </hierarchical-property-node>
     <hierarchical-property-node name="Asia">
      <hierarchical-property-node name="Japan">
      </hierarchical-property-node>
      <hierarchical-property-node name="Singapore">
      </hierarchical-property-node>
      <hierarchical-property-node name="China">
      </hierarchical-property-node>
      <hierarchical-property-node name="India">
      </hierarchical-property-node>
     </hierarchical-property-node>
     <hierarchical-property-node name="EMEA">
      <hierarchical-property-node name="Europe">
       <hierarchical-property-node name="European Union">
        <hierarchical-property-node name="Euro Zone">
         <hierarchical-property-node name="France">
         </hierarchical-property-node>
         <hierarchical-property-node name="Spain">
         </hierarchical-property-node>
         <hierarchical-property-node name="Germany">
         </hierarchical-property-node>
         <hierarchical-property-node name="Italy">
         </hierarchical-property-node>
         <hierarchical-property-node name="Belgium">
         </hierarchical-property-node>
         <hierarchical-property-node name="Greece">
         </hierarchical-property-node>
         <hierarchical-property-node name="Ireland">
         </hierarchical-property-node>
         <hierarchical-property-node name="Portugal">
         </hierarchical-property-node>
         <hierarchical-property-node name="Austria">
         </hierarchical-property-node>
         <hierarchical-property-node name="Finland">
         </hierarchical-property-node>
         <hierarchical-property-node name="Luxembourg">
         </hierarchical-property-node>
         <hierarchical-property-node name="Netherlands">
         </hierarchical-property-node>
        </hierarchical-property-node>
        <hierarchical-property-node name="United Kingdom">
         <hierarchical-property-node name="England">
         </hierarchical-property-node>
         <hierarchical-property-node name="Wales">
         </hierarchical-property-node>
         <hierarchical-property-node name="Scotland">
         </hierarchical-property-node>
        </hierarchical-property-node>
        <hierarchical-property-node name="Poland">
        </hierarchical-property-node>
       </hierarchical-property-node>
       <hierarchical-property-node name="Switzerland">
       </hierarchical-property-node>
       <hierarchical-property-node name="Russia">
       </hierarchical-property-node>
       <hierarchical-property-node name="Denmark">
       </hierarchical-property-node>
       <hierarchical-property-node name="Ukraine">
       </hierarchical-property-node>
      </hierarchical-property-node>
      <hierarchical-property-node name="Middle East">
       <hierarchical-property-node name="Egypt">
       </hierarchical-property-node>
       <hierarchical-property-node name="Israel">
       </hierarchical-property-node>
       <hierarchical-property-node name="Saudi Arabia">
       </hierarchical-property-node>
       <hierarchical-property-node name="United Arab Emirates">
       </hierarchical-property-node>
      </hierarchical-property-node>
      <hierarchical-property-node name="Africa">
       <hierarchical-property-node name="Morocco">
       </hierarchical-property-node>
       <hierarchical-property-node name="Algeria">
       </hierarchical-property-node>
       <hierarchical-property-node name="South Africa">
       </hierarchical-property-node>
      </hierarchical-property-node>
     </hierarchical-property-node>
     <hierarchical-property-node name="South Pacific">
      <hierarchical-property-node name="Australia">
      </hierarchical-property-node>
      <hierarchical-property-node name="New Zealand">
      </hierarchical-property-node>
     </hierarchical-property-node>
    </hierarchical-property-node>
   </hierarchical-property>
  </hierarchical-properties>
  <propertyDefinitions>
   <propertyType name="effectiveDate" className="java.util.Date"/>
   <propertyType name="expirationDate" className="java.util.Date"/>
   <propertyType name="status" className="java.lang.String"/>
   <propertyType name="priority" className="java.lang.String"/>
   <propertyType name="customers" className="java.lang.String"/>
   <propertyType name="industry" className="Industry"/>
   <propertyType name="location" className="Geography"/>
   <propertyType name="ilog.rules.package" className="ilog.rules.PackageHierarchy"/>
  </propertyDefinitions>
  <packageVariables>
  </packageVariables>
 </default-package>
 <package pkgName="computation">
  <packageVariables>
  </packageVariables>
  <rule name="salary2score_0">
   <comment>
 * f
 </comment>
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.salary2score&quot;"/>
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
  <rule name="rate_0">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.rate&quot;"/>
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
  <rule name="bankruptcyScore_0">
   <properties>
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.bankruptcyScore&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.bankruptcyScore&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.bankruptcyScore&quot;"/>
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
    <property name="ilog.rules.dt">
     <constant type="string" value="&quot;computation.bankruptcyScore&quot;"/>
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
</ruleset>
