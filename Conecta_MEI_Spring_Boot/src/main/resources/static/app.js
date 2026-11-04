'use strict';
const $=s=>document.querySelector(s), $$=s=>[...document.querySelectorAll(s)];
const now=new Date(), currentMonth=`${now.getFullYear()}-${String(now.getMonth()+1).padStart(2,'0')}`;
const todayString=`${currentMonth}-${String(now.getDate()).padStart(2,'0')}`;
let db={transactions:[],products:[]}, dashboard={revenues:0,expenses:0,balance:0,count:0};
const money=n=>Number(n).toLocaleString('pt-BR',{style:'currency',currency:'BRL'});
const date=d=>{const [y,m,day]=d.split('-');return `${day}/${m}/${y}`};
const safe=s=>String(s).replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
async function api(path,options={}){
 const r=await fetch('/api'+path,{headers:{'Content-Type':'application/json'},...options});
 if(!r.ok){let msg='Erro na operação';try{const obj=await r.json();msg=obj.details?.join('; ')||obj.error||obj.detail||msg;}catch{}throw Error(msg+' (HTTP '+r.status+')');}
 return r.status===204?null:r.json();
}
async function refresh(){
 try{const month=$('#month').value;
  const [transactions,products,summary]=await Promise.all([api('/transactions'),api('/products'),api('/dashboard?month='+encodeURIComponent(month))]);
  db={transactions,products};dashboard=summary;render();
 }catch(e){alert('Não foi possível carregar os dados: '+e.message);}
}
function render(){
 $('#demo-banner').classList.remove('hidden');
 const month=$('#month').value,tx=db.transactions.filter(t=>t.date.startsWith(month));
 const revenues=Number(dashboard.revenues), expenses=Number(dashboard.expenses), balance=Number(dashboard.balance);
 $('#revenue').textContent=money(revenues);$('#expenses').textContent=money(expenses);$('#balance').textContent=money(balance);$('#count').textContent=dashboard.count;
 const max=Math.max(revenues,expenses,1);
 $('#chart').innerHTML=[['Receitas',revenues,''],['Despesas',expenses,'expense']].map(([name,val,style])=>`<div class="bar-row"><span>${name}</span><div class="bar-track"><div class="bar-fill ${style}" style="width:${100*val/max}%"></div></div><span class="bar-value">${money(val)}</span></div>`).join('');
 $('#summary').innerHTML=`<div class="summary-line"><span>Receitas registradas</span><strong>${money(revenues)}</strong></div><div class="summary-line"><span>Despesas registradas</span><strong>${money(expenses)}</strong></div><div class="summary-line"><span>Saldo apurado</span><strong>${money(balance)}</strong></div><div class="summary-line"><span>Situação do período</span><strong>${balance>=0?'Saldo não negativo':'Despesas superiores às receitas'}</strong></div>`;
 const rows=[...db.transactions];
 const row=(t,canDelete)=>`<tr><td>${safe(t.description)}</td>${canDelete?`<td>${safe(t.category)}</td>`:''}<td><span class="pill ${t.type==='DESPESA'?'out':''}">${t.type==='RECEITA'?'Receita':'Despesa'}</span></td><td>${date(t.date)}</td><td class="right">${money(t.amount)}</td>${canDelete?`<td class="right"><button class="delete-btn" data-delete="${t.id}" aria-label="Excluir ${safe(t.description)}">Excluir</button></td>`:''}</tr>`;
 $('#recent').innerHTML=rows.slice(0,5).map(t=>row(t,false)).join('')||'<tr><td colspan="4" class="empty">Nenhum lançamento cadastrado.</td></tr>';
 const chosen=$('#filter-type').value;
 const visible=chosen==='all'?rows:rows.filter(t=>t.type===chosen.toUpperCase());
 $('#all-transactions').innerHTML=visible.map(t=>row(t,true)).join('')||'<tr><td colspan="6" class="empty">Nenhum lançamento encontrado.</td></tr>';
 $('#product-list').innerHTML=db.products.map(p=>`<article class="product-card"><strong>${safe(p.name)}</strong><div class="product-meta"><span>${safe(p.type)}</span><b>${money(p.price)}</b></div><button class="delete-btn" data-product-delete="${p.id}">Excluir item</button></article>`).join('')||'<article class="panel">Nenhum item cadastrado. Clique em “Adicionar item”.</article>';
}
function navigate(name){$$('.view').forEach(s=>s.classList.toggle('hidden',s.id!==name));$$('.nav').forEach(s=>s.classList.toggle('active',s.dataset.view===name));$('#page-title').textContent=({dashboard:'Visão geral',transactions:'Vendas e despesas',products:'Produtos e serviços',help:'Orientações'})[name];}
$$('.nav').forEach(b=>b.addEventListener('click',()=>navigate(b.dataset.view)));
$$('[data-goto]').forEach(b=>b.addEventListener('click',()=>navigate(b.dataset.goto)));
$('#month').value=currentMonth;$('#month').addEventListener('change',refresh);$('#filter-type').addEventListener('change',render);
$('#new').addEventListener('click',()=>{$('#tx-form').reset();$('#tx-form').elements.date.value=todayString;$('#tx-dialog').showModal()});
$('#new-product').addEventListener('click',()=>{$('#product-form').reset();$('#product-dialog').showModal()});
$$('[data-close]').forEach(b=>b.addEventListener('click',()=>b.closest('dialog').close()));
$('#tx-form').addEventListener('submit',async e=>{e.preventDefault();const f=new FormData(e.target);try{await api('/transactions',{method:'POST',body:JSON.stringify({description:String(f.get('description')).trim(),type:String(f.get('type')).toUpperCase(),category:f.get('category'),amount:Number(f.get('amount')),date:f.get('date')})});$('#tx-dialog').close();await refresh();}catch(err){alert(err.message);}});
$('#product-form').addEventListener('submit',async e=>{e.preventDefault();const f=new FormData(e.target);try{await api('/products',{method:'POST',body:JSON.stringify({name:String(f.get('name')).trim(),type:f.get('type'),price:Number(f.get('price'))})});$('#product-dialog').close();await refresh();}catch(err){alert(err.message);}});
document.addEventListener('click',async e=>{const t=e.target.closest('[data-delete],[data-product-delete]');if(!t||!confirm('Confirma a exclusão deste registro?'))return;try{await api(t.dataset.delete?'/transactions/'+t.dataset.delete:'/products/'+t.dataset.productDelete,{method:'DELETE'});await refresh();}catch(err){alert(err.message);}});
$('#export').addEventListener('click',()=>{const header=['Descrição','Tipo','Categoria','Valor (R$)','Data'];const records=db.transactions.map(t=>[t.description,t.type,t.category,Number(t.amount).toFixed(2).replace('.',','),t.date]);const quote=v=>'"'+String(v).replace(/"/g,'""')+'"';const csv='\uFEFF'+[header,...records].map(a=>a.map(quote).join(';')).join('\r\n');const blob=new Blob([csv],{type:'text/csv;charset=utf-8'}),url=URL.createObjectURL(blob),a=document.createElement('a');a.href=url;a.download='conecta-mei-lancamentos.csv';a.click();setTimeout(()=>URL.revokeObjectURL(url),1000)});
refresh();
