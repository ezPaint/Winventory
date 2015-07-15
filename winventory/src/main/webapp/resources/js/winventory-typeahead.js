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
							var hardwareTypeBloodhound = new Bloodhound({

									// array is in form:  [   "typeOne", "typeTwo", ... ]
									datumTokenizer : Bloodhound.tokenizers.whitespace,
									queryTokenizer : Bloodhound.tokenizers.whitespace,

									// fetch types from TypeaheadController.java
									prefetch: {
										url: '../typeahead?data=hardwareTypes',
										filter: properCaseFilter
									}

							});
							
							// clear cache and initialize for bloodhound
							hardwareTypeBloodhound.clearPrefetchCache();
							hardwareTypeBloodhound.initialize();

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
							
							
							
							
							// USERNAME TYPEAHEAD:
							
							// Bloodhound for listing usernames
							var usernameBloodhound = new Bloodhound(
									{
										// array is in form: ["username1", "username2", ...]
										datumTokenizer : Bloodhound.tokenizers.whitespace,
										queryTokenizer : Bloodhound.tokenizers.whitespace,
										
										// fetch types from TypeaheadController.java
										prefetch: '../typeahead?data=usernames'
									}
							);
							
							// clear cache and initialize for bloodhound
							usernameBloodhound.clearPrefetchCache();
							usernameBloodhound.initialize();
							
							// set the typeahead function for the db input (id="db")
							$('.username-typeahead').typeahead({
								hint : true,
								highlight : true,
								minLength : 1,
							},

							{ // sets hardwareTypeBloodhound as the data source
								name: 'username',
								source : usernameBloodhound, // Bloodhounds get the data
								limit : 1000 //max number of suggestions
							});
							
							
							
							
							
							
						});