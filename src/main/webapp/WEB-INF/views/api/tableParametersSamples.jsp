<table class="table">
	<thead>
		<tr>
			<th>Key</th>
			<th>Value</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>document</td>
			<td>
				<ul>
					<li><b>exp_group</b>: download standard experimental grouping, DEFAULT</li>
					<li><b>parameters</b>: download original parameters</li>
				</ul>
			</td>
		</tr>
		<tr>
			<td>format</td>
			<td>
				<ul>
					<li><b>csv</b>: create CSV file, DEFAULT</li>
					<li><b>json</b>: generate a result summary in JSON format</li>
				</ul>
			</td>
		</tr>
		<tr>
			<td>id_sample</td>
			<td>ID number(s) of samples, for example, id_sample=GSM748053,GSM748054</td>
		</tr>
		<tr>
			<td>series</td>
			<td>a list of series, for example, series=GSE30219,GSE2109</td>
		</tr>
		<tr>
			<td>id_platform</td>
			<td>a list of platforms, for example, id_platform=GPL96,GPL97</td>
		</tr>
		<tr>
			<td>sex</td>
			<td>F, M</td>
		</tr>
		<tr>
			<td>age_min</td>
			<td>minimal age</td>
		</tr>
		<tr>
			<td>age_max</td>
			<td>maximal age</td>
		</tr>
		
		<tr>
			<td>id_tissue_stage</td>
			<td>
				<ul>
					<li><b>1</b>: adult</li>
					<li><b>2</b>: fetal</li>
					<li><b>3</b>: not relevant</li>
					<li><b>4</b>: embryonic</li>
				</ul>
			</td>
		</tr>
		
		<tr>
			<td>id_tissue_status</td>
			<td>
				<ul>
					<li><b>1</b>: Normal</li>
					<li><b>2</b>: Pathological non tumoral</li>
					<li><b>3</b>: Pathological tumoral</li>
				</ul>
			</td>
		</tr>
		
		<tr>
			<td>id_pathology</td>
			<td>ICD-10 pathology code</td>
		</tr>
	
		<tr>
			<td>collection_method</td>
			<td>biopsy, cell line, isolated cells</td>
		</tr>
		
		<tr>
			<td>id_topology</td>
			<td>ICD-O topology code</td>
		</tr>
		
		<tr>
			<td>id_moprhology</td>
			<td>ICD-O morphology code</td>
		</tr>
		
		<tr>
			<td>survival</td>
			<td>samples with survival only</td>
		</tr>
		
	</tbody>
</table>