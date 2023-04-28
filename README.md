# SET08103  
&emsp;&emsp;<u>Group Project</u>  

&emsp;&emsp;![workflow](https://github.com/Benbhoy1888/SET08103/actions/workflows/main.yml/badge.svg?style=flat)  
* License:&emsp;&emsp;&emsp;&emsp;&emsp;&ensp;&nbsp;&nbsp; ![GitHub](https://img.shields.io/github/license/Benbhoy1888/SET08103?style=flat)  
* Release:&emsp;&emsp;&emsp;&emsp;&emsp;&ensp;&ensp; [![Releases](https://img.shields.io/github/release/Benbhoy1888/SET08103/all.svg?style=flat-square)](https://github.com/Benbhoy1888/SET08103/releases&style=flat)  
* Main Build Status:&emsp;&ensp;&nbsp; ![GitHub Workflow Status (with branch)](https://img.shields.io/github/actions/workflow/status/Benbhoy1888/SET08103/main.yml?branch=master&style=flat)  
* Release Build Status: &ensp;![GitHub Workflow Status (with branch)](https://img.shields.io/github/actions/workflow/status/Benbhoy1888/SET08103/main.yml?branch=release&style=flat)   
* Develop Build Status: &nbsp;![GitHub Workflow Status (with branch)](https://img.shields.io/github/actions/workflow/status/Benbhoy1888/SET08103/main.yml?branch=develop&style=flat) 
* CodeCov Build Status:	[![codecov](https://codecov.io/gh/Benbhoy1888/SET08103/branch/master/graph/badge.svg?token=Z14TRAVG1B)](https://codecov.io/gh/Benbhoy1888/SET08103)    
<br>


| ID | Name | Met | Screenshot |
| :---: | :---: | :---: | :---: |
| 1. | All the countries in the world organised by largest population to smallest. | Yes | ![countries - world](requirments_screenshots/allWorldCountries.png) |
| 2. | All the countries in a continent organised by largest population to smallest. | Yes | ![countries - continent](requirments_screenshots/allCountriesContinent.png) |
| 3. | All the countries in a region organised by largest population to smallest. | Yes | ![countries - region](requirments_screenshots/allCountriesRegion.png) |
| 4. | The top N populated countries in the world where N is provided by the user. | Yes | ![countries - world n](requirments_screenshots/top5_worldCountries.png) |
| 5. | The top N populated countries in a continent where N is provided by the user. | Yes | ![countries - continent n](requirments_screenshots/top8_continentCountries.png) |
| 6. | The top N populated countries in a region where N is provided by the user. | Yes | ![countries - region n](requirments_screenshots/top3_regionCountries.png) |
| 7. | All the cities in the world organised by largest population to smallest. | Yes | ![cities - world](requirments_screenshots/worldCities.png) |
| 8. | All the cities in a continent organised by largest population to smallest. | Yes | ![cities - continent](requirments_screenshots/continentCities.png) |
| 9. | All the cities in a region organised by largest population to smallest. | Yes | ![cities - region](requirments_screenshots/regionCities.png) | 
| 10. | All the cities in a country organised by largest population to smallest. | Yes | ![cities - country](requirments_screenshots/countryCities.png) |
| 11. | All the cities in a district organised by largest population to smallest. | Yes | ![cities - district](requirments_screenshots/districtCities.png) |
| 12. | The top N populated cities in the world where N is provided by the user. | Yes | ![cities - world n](requirments_screenshots/top5_citiesWorld.png) |
| 13. | The top N populated cities in a continent where N is provided by the user. | Yes | ![cities - continent n](requirments_screenshots/top8_citiesContinent.png) | 
| 14. | The top N populated cities in a region where N is provided by the user. | Yes | ![cities - region n](requirments_screenshots/top3_ citiesRegion.png) |
| 15. | The top N populated cities in a country where N is provided by the user. | Yes | ![cities - country n](requirments_screenshots/top5_citiesCountry.png) |
| 16. | The top N populated cities in a district where N is provided by the user. | Yes | ![cities - district n](requirments_screenshots/top1_citiesDistrict.png) |
| 17. | All the capital cities in the world organised by largest population to smallest. | No | ![capital cities - world](requirments_screenshots/FILENAME_HERE) |
| 18. | All the capital cities in a continent organised by largest population to smallest. | No | ![capital cities - continent](requirments_screenshots/FILENAME_HERE) |
| 19. | All the capital cities in a region organised by largest to smallest. | No | ![capital cities - region](requirments_screenshots/FILENAME_HERE) | 
| 20. | The top N populated capital cities in the world where N is provided by the user. | No | ![capital cities - world n](requirments_screenshots/FILENAME_HERE) | 
| 21. | The top N populated capital cities in a continent where N is provided by the user. | No | ![capital cities - continent n](requirments_screenshots/FILENAME_HERE) | 
| 22. | The top N populated capital cities in a region where N is provided by the user. | No | ![capital cities - region n](requirments_screenshots/FILENAME_HERE) | 
| 23. | The population of people, people living in cities, and people not living in cities in each continent. | Yes | ![urbanisation - continent](requirments_screenshots/urbanPopulationContinent.png) | 
| 24. | The population of people, people living in cities, and people not living in cities in each region. | Yes | ![urbanisation - region](requirments_screenshots/urbanPopulationRegion.png) | 
| 25. | The population of people, people living in cities, and people not living in cities in each country. | Yes | ![urbanisation - country](requirments_screenshots/urbanPopulationCountry.png) | 
| 26. | The population of the world. | Yes | ![total population - world](requirments_screenshots/totalPopulationWorld.png) | 
| 27. | The population of a continent. | Yes | ![total population - continent](requirments_screenshots/totalPopulationContinent.png) | 
| 28. | The population of a region. | Yes | ![total population - region](requirments_screenshots/totalPopulationRegion.png) | 
| 29. | The population of a country. | Yes | ![total population - country](requirments_screenshots/totalPopulationCountry.png) | 
| 30. | The population of a district. | Yes | ![total population - district](requirments_screenshots/totalPopulationDistrict.png) | 
| 31. | The population of a city. | Yes | ![total population - city](requirments_screenshots/totalPopulationCity.png) | 
| 32. | The number of people who speak the following the following languages from greatest number to smallest,<br>including the percentage of the world population:<br> Chinese, English, Hindi, Spanish, Arabic. | Yes | ![languages](requirments_screenshots/languages.png) | 



Notes:  
To up-date version and create a release-
* Update in pom.xml (version) and Dockerfile (copy and entry points)
* Rebuild and retest
* Commit and push
* Merge to develop and push
* Merge to release
* From release select 'new tag' using version as tag name (format = v0.1-alpha-2 etc.)
* Push with 'Push tags' checked
* On GitHub, click 'create a new release' - use pre-release if applicable, choose correct tag, add description and comments
* Merge release to master
* Merge release to develop
* Checkout feature branch  
* 11/04/2023 push to check if deployment to Gitub
* 17/04/2023 push to check if deployment ot GitHub is
* 21/04/2023 junit packages reloaded and pushed
* 21/04/2023 added deloyment permissions to main.xml
* 24/04/2023 re-push to correct mising objects in languages unit test