package org.esmerilprogramming.overtown.management;

import org.esmerilprogramming.overtown.annotation.Controller;
import org.esmerilprogramming.overtown.annotation.path.Get;
import org.esmerilprogramming.overtown.http.HttpResponse;
import org.esmerilprogramming.overtown.http.OvertownRequest;


@Controller(path = "")
public class RootController {
	
	
	@Get(value="")
	public void home(OvertownRequest request){
		( (HttpResponse)request.getResponse() ).sendAsResponse("LOL ? ");
	}

}

