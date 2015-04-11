<#import "/main-template.ftl" as p>
<@p.page>
	<form action="toJson" method="post">
		<div>
			<label>Name</label>
			<input type="text" name="contact.name" id="name" />
		</div>
        <div>
            <label>Email</label>
            <input type="email" name="contact.email" id="email" />
        </div>
        <div>
            <label>Celphone</label>
            <input type="text" name="contact.celphone" id="celphone" />
        </div>
		<button type="submit">Convert to Json</button>
	</form>
</@p.page>