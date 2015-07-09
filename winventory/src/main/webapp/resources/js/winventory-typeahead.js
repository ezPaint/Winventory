		$(document)
				.ready(
						function() {
							
							// capitalizes the first letter of each word in string, lower-cases the rest
							var properCase = function(str) {

								var rawWords = str.split(' ');
								var resultWords = [];

								// for each word: upper-case first letter, lower-case rest
								for (var i = 0; i < rawWords.length; i++) {
									resultWords.push(rawWords[i]
											.substring(0, 1).toUpperCase()
											+ rawWords[i].substring(1)
													.toLowerCase());
								}

								var result = resultWords.join(' ');
								return result;
							};
							
							// properCases the elements in the array
							var properCaseFilter = function(responseArray)  {
								// properCase the strings in the array
								return $.map(
												responseArray,
												function(element) {
													return properCase(element);
												}
										);
							};

							// Bloodhound for listing hardware type options
							var hardwareTypeBloodhound = new Bloodhound(
									{

									// array is in form:  [   "typeOne", "typeTwo", ... ]
									datumTokenizer : Bloodhound.tokenizers.whitespace,
									queryTokenizer : Bloodhound.tokenizers.whitespace,

									// fetch types from resources/json/hardwareType.json
									prefetch: {
										url: '../typeahead?data=hardwareTypes',
										filter: properCaseFilter
									}

							});
							
							hardwareTypeBloodhound.clearPrefetchCache();
							hardwareTypeBloodhound.initialize();
							
							// random name for the data source acts as workaround for caching issue
							// the caching issue: if new hardwareTypes are added, they don't show up because
							// the browser caches the data source
							var randLetter = String.fromCharCode(65 + Math.floor(Math.random() * 26));
							var randomUniqueId = randLetter + Date.now();

							// set the typeahead function for the db input (id="db")
							$('.search-hardware-type').typeahead({
								hint : true,
								highlight : true,
								minLength : 1,
							},

							{ // sets hardwareTypeBloodhound as the data source
								name: 'hardwareType',
								source : hardwareTypeBloodhound, // Bloodhounds get the data
								limit : 100 //max number of suggestions
							});
						});