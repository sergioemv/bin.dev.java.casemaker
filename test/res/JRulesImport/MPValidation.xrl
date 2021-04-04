<?xml version="1.0" encoding="ISO-8859-1"?>
<ruleset name="loanvalidation$45$validation" className="ilog.rules.engine.IlrContext" xmlns="http://www.ilog.com/products/xml/schemas/xrl_2.0">
 <rulesetParameters>
  <argument modifier="3" type="loan.Borrower" name="validation_borrower">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="7" type="loan.Loan" name="validation_loan">
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
 </default-package>
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
 </package>
 <package pkgName="validation">
  <packageVariables>
  </packageVariables>
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
 <package pkgName="translation.loan.Borrower">
  <packageVariables>
  </packageVariables>
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
</ruleset>
