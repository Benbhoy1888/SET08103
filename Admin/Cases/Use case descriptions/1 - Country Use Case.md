# **USE CASE: Produce a report that shows the countries organized by population large to small for; the world, a continent or a region.** 

## **CHARACTERISTIC INFORMATION**

### **Goal in Context**

As a healthcare assistant, I want to produce reports that show the
countries organized by population large to small for; the world, a
continent or a region.

### **Scope**

NHS

### **Level**

Primary task

### **Preconditions**

We know the role. The database contains country data by population.

### **Success End Condition**

A report is available for the NHS to provide to the health care
assistant

### **Failed End Condition**

No report is produced

**Primary Actor**

Healthcare Research Assistant

### **Trigger**

The research assistant selects country reports from main menu

## **MAIN SUCCESS SCENARIO**

1.  The research assistant is presented with a menu to select from
    world, continent or region

2.  If continent or region is selected the research assistant will be
    requested to enter a continent or region

3.  If the research assistant selects world in step 1, the menu skips
    step 2

4.  The research assistant is requested to input the number of results
    to show or they just hit enter to see all results

5.  Report should be generated with the columns: Code, Name, Continent,
    Region, Population and Capital
